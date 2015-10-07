**A simple app server for sending push notofocations to Android devices. Powered by [Dropwizard](http://www.dropwizard.io/)**

# Build and run

First, you will need a valid configuration file. In your terminal type
```sh
$ cd /path/to/project/src/main/resources
$ cp example_config.yml config.yml
```
and replace *apiKey* value with API key obtained from Google. Configuration manual can be found [here](http://www.dropwizard.io/manual/configuration.html).

To compile and run this application just type the following in your terminal
```sh
$ mvn compile exec:java
```
