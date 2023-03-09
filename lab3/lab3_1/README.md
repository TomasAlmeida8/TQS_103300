# Questions in Lab 3.1

## a) Identify a couple of examples that use AssertJ expressive methods chaining.**
```java
assertThat(fromDb.getEmail()).isEqualTo( emp.getEmail());
assertThat(fromDb).isNull();
assertThat(found.getName()).isEqualTo(name);
```
## b) Identify an example in wich you mock the behavior of the repository (and avoid involving a database).**
```java
@BeforeEach
    public void setUp() {

        //these expectations provide an alternative to the use of the repository
        Employee john = new Employee("john", "john@deti.com");
        john.setId(111L);

        Employee bob = new Employee("bob", "bob@deti.com");
        Employee alex = new Employee("alex", "alex@deti.com");

        List<Employee> allEmployees = Arrays.asList(john, bob, alex);

        Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
        Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
        Mockito.when(employeeRepository.findByName("wrong_name")).thenReturn(null);
        Mockito.when(employeeRepository.findById(john.getId())).thenReturn(Optional.of(john));
        Mockito.when(employeeRepository.findAll()).thenReturn(allEmployees);
        Mockito.when(employeeRepository.findById(-99L)).thenReturn(Optional.empty());
    }
```

## c) What is the difference between standard @Mock and @MockBean?**

@Mock is the default way to mock something in a Java app. @MockBean is used to Mock something in a Spring Boot app.

## d) What is the role of the file "application-integrationtest.properties"? In wich conditions will it be used?**
It is used to configure the application when running integration tests (instead of using the default application.properties file). This way we can use different configurations for different types of tests.

## e) The sample project demonstrates three strategies to assess an API (C, D and E) developed with SpringBoot. Wich are the main/key differences?**
C runs faster because it doesn't start the whole application, but only the controller. D and E are more realistic because they start the whole application.
However, D mocks a servelet while in E we have an actual REST client.
