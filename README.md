# rest-utils

Simple and transparent utilities to easily test REST APIs.

## Introduction

To properly test REST APIs, there are a couple of things you need:
1. JSON (de)serialization
2. The ability to perform HTTP calls
3. A testing framework
4. Use of the above, with little effort and lots of convenience (but not too much magic)

The Java ecosystem provides plenty of choices with regard to the first three (e.g. Apache Commons, Spring, JUnit, Gson), but due to clunky and clumsy library interfaces, it's somewhat lacking in the fourth. Since it's ultimately humans who write API tests, the last point on the list however makes all the difference between having *some tests, for the simple cases and main flows of your API* versus having *lots of tests, for all the complex cases and miscellaneous flows of your API*.

The various `rest-utils` libraries aim to provide a convenient way to write API tests, in plain Java. To this end, they leverage the reliabity of existing libraries regarding JSON and HTTP, but offer simple and transparant library interfaces. The latter can be found under [json-utils](https://github.com/alainvanhout/rest-utils/tree/master/json-utils) and [http-utils](https://github.com/alainvanhout/rest-utils/tree/master/http-utils), while corresponding implementations (based on Gson and on Apache HttpComponents, respectively) can be found under [json-utils-gson](https://github.com/alainvanhout/rest-utils/tree/master/json-utils-gson) and [http-utils-apachecommons](https://github.com/alainvanhout/rest-utils/tree/master/http-utils-apachecommons).

To perform actual API tests more easily, [endpoint-utils](https://github.com/alainvanhout/rest-utils/tree/master/endpoint-utils) provides the means to write clients for specific REST APIs. Of course, such clients can just well be used outside of the context of testing, as is also true for `json-utils` and `http-utils`.
