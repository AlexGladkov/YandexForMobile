#!/bin/bash

set -e

pushd yandexPro
flutter pub get && flutter build aar
popd