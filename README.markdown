# Offline Articles

Parse-backed for reading articles on the Tube. Doesn't work yet.

## Setup

Go to [parse.com](https://parse.com/), create a new app, add your keys to a new
`gradle.properties` in the project root like this:

```
parseApplicationId=<application_id>
parseClientKey=<client_key>
```

You can build and install it afterwards like this:

```bash
./gradlew :app:installDebug
```
