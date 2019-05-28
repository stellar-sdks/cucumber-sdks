# Cucumber testing for Stellar SDKs

This project's goal is to test Stellar SDKs at a high level, in order to validate that
each SDK interacts with Stellar networks as expected.

The functionality tested is documented inside the [features](features) folder and is the same
for each SDK under test.

The SDKs being tested are defined as sub-projects in their own folders:

* [Kotlin](inbot-stellar-kotlin-wrapper)
* [Scala](scala-stellar-sdk)

## Adding new SDKs

1. Modify `.travis.yml`'s `matrix` block to include a new entry, specifying the language and an 
    environment variable `SDK_PROJECT=$folder_name`
2. Build a runnable cucumber test project inside `folder_name` that depends upon the latest
    binary for the target SDK and provides the glue for the Cucumber steps.
    Cucumber should be configured to find the features in the shared relative path `../features`.
3. Create a file `travis.sh` at the base of your sub-project that will invoke your test project. 
    This will be executed by the build.