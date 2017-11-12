# Petclinic demo

The purpose of this project is to give an example usage of the different sub-libraries of http-utils
- [http-utils](https://github.com/alainvanhout/rest-utils/tree/master/http-utils)
- [json-utils](https://github.com/alainvanhout/rest-utils/tree/master/json-utils)
- [endpoint-utils](https://github.com/alainvanhout/rest-utils/tree/master/endpoint-utils)

More specifically, it aims to show how they can work in concert to perform API tests at varying abstraction levels.

A Spring Boot REST API [implementation](https://github.com/spring-petclinic/spring-petclinic-rest) of the Spring petclinic [project](https://github.com/spring-petclinic) was chosen to test against, so that the subject under testing would be representative of REST APIs in general, and would not tailored to rest-utils itself.
