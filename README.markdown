# Offline Articles

Parse-backed for reading articles on the Tube. **Doesn't work yet.**

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

## Ideas

I thought about rewriting the HTML when saving it, same for the CSS. But this
seems incredibly painful and prone to break. It's easy to miss a reference,
rewrite it incorrectly and saving it is difficult. Depending on how far you
flatten the structure you may overwrite something generic like "button.png"
multiple times. It seems more reliable to run a local HTTP server and create a
mapping between the real URL and a local unique identifier that can easily be
saved in a flat directory. What's still going to break is everything JS-based.
I'm not even going to try to extract URLs from JavaScript. Not in the mood for
solving the Halting Problem today.
