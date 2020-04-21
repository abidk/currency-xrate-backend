# currency-xrate-backend
Purpose of this task is to create small system for storing and viewing currency exchange rates.

## Run application
Running the application locally:
  - Type ```./gradlew clean bootRun``` into terminal
  - Send GET request to ```http://localhost:8080/rates/EUR/2011-01-01```

You can specify the base currency and the date which will then forward the request to the rates api. Please the documentation for the APi here... ```https://ratesapi.io/documentation/```