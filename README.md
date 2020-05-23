# elimu.ai Appstore

Android application which downloads educational apps stored on the [elimu.ai](http://elimu.ai) platform.

## Download application ⬇️

Download APK at https://github.com/elimu-ai/appstore/releases

## Software architecture

See https://github.com/elimu-ai/wiki/blob/master/SOFTWARE_ARCHITECTURE.md

## What devices are being used?

We are building our software for Android devices with 6"-10" displays installed with Android API **version 24 (7.0)** or higher.

## Development 👩🏽‍💻

Note that the `REST_URL` depends on the build type you choose when installing the app:
  * `debug`: http://`<language>`.**test**.elimu.ai/rest/
  * `release`: http://`<language>`.elimu.ai/rest/

### Supported languages

A list of the currently supported languages is available at https://github.com/elimu-ai/model/blob/master/src/main/java/ai/elimu/model/enums/Language.java

The first time you launch the Appstore application, it will ask you to select the language that you want to use from a drop-down menu.

For information on how to add support for a new language, see https://github.com/elimu-ai/wiki/blob/master/LOCALIZATION.md.

---

## About the elimu.ai Community

![elimu ai-tagline](https://user-images.githubusercontent.com/15718174/54360503-e8e88980-465c-11e9-9792-32b513105cf3.png)

 * For a high-level description of the project, see https://github.com/elimu-ai/wiki/blob/master/README.md.
 * For project milestones, see https://github.com/elimu-ai/wiki/projects.
