#!/bin/bash

ARCH_SUFFIX=""
X86="x86_64"
ARM="arm64"
COLOR_OFF='\033[0m'
COLOR_BOLD_LIGHT_GREEN="\033[1;92m"
COLOR_BOLD_LIGHT_RED="\033[1;91m"
COLOR_BOLD_WHITE="\033[1;37m"

function defineArch() {
  case "$(uname -s)" in
    Darwin)
      ARCH="$(uname -m)"
      case "$ARCH" in
      $X86) ;;
      $ARM) ;;
      *) echo "Unsupported arch $ARCH!"; exit 1; ;;
      esac
      ;;
    *) echo "Unsupported OS!"; exit 1; ;;
  esac
}

function colorEcho() {
  color=$1
  text=$2
  echo -e "${color}$text${COLOR_OFF}"
}

function errorEcho() {
  colorEcho "$COLOR_BOLD_LIGHT_RED" "$1"
}

function successEcho() {
  colorEcho "$COLOR_BOLD_LIGHT_GREEN" "$1"
}

function infoEcho() {
  colorEcho "$COLOR_BOLD_WHITE" "$1"
}

function updatePath() {
  checkString=$1
  newLine=$2

  pathString=$(echo $PATH)
  if [[ $pathString != *"$checkString"* ]]; then
    if [[ -f ~/.zshenv ]] && [[ $(cat ~/.zshenv) == *"$newLine"* ]]; then
      return
    fi
    echo "export PATH=$newLine:\$PATH" >> ~/.zshenv
    source ~/.zshenv
  fi
}

function configureFlutter() {
  TAG="[Flutter]"
  infoEcho "$TAG —> Check flutter"
  which flutter
  ret_code=$?
  if [[ $ret_code == 0 ]] ; then
    infoEcho "$TAG —> configuration skipped"
    return
  fi

  FLUTTER_URL=""
  if [[ $ARCH == $ARM ]]; then
    infoEcho "$TAG —> Init rosetta"
    sudo softwareupdate --install-rosetta --agree-to-license || exit 1
    FLUTTER_URL="https://storage.googleapis.com/flutter_infra_release/releases/stable/macos/flutter_macos_arm64_3.22.0-stable.zip"
  else
    FLUTTER_URL="https://storage.googleapis.com/flutter_infra_release/releases/stable/macos/flutter_macos_3.22.0-stable.zip"
  fi

  infoEcho "$TAG —> Download flutter SDK"
  curl --fail "$FLUTTER_URL" -o flutter-sdk.zip || exit 1
  if [ ! -d ~/development ]; then
    mkdir ~/development || exit 1
  fi

  infoEcho "$TAG —> Unzip flutter SDK"
  yes | 1>/dev/null unzip flutter-sdk.zip -d ~/development/ || exit 1
  rm flutter-sdk.zip
  updatePath "$HOME/development/flutter/bin" "\$HOME/development/flutter/bin"

  successEcho "$TAG —> Flutter configured"
}

function configureIos() {
  TAG="[iOS]"
  infoEcho "$TAG —> Verify xcodebuild"
  which xcodebuild
  ret_code=$?
  if [[ $ret_code != 0 ]] ; then
    errorEcho "XCode not found. Please install it before"
    exit 1
  fi
  infoEcho "$TAG —> Verify xcode-select"
  1>/dev/null xcode-select -p
  ret_code=$?
  if [[ $ret_code == 0 ]] ; then
    updatePath "$HOME/.gem/bin" "\$HOME/.gem/bin"
    infoEcho "$TAG —> iOS configuration skipped"
    return
  fi
  infoEcho "$TAG —> Select Xcode"
  sudo sh -c 'xcode-select -s /Applications/Xcode.app/Contents/Developer && xcodebuild -runFirstLaunch' || exit 1
  yes | sudo xcodebuild -license || exit 1

  infoEcho "Install cocoapods"
  sudo gem install cocoapods || exit 1
  updatePath "$HOME/.gem/bin" "\$HOME/.gem/bin"

  successEcho "$TAG —> iOS configured"
}

# Start bootstrap
infoEcho "————> Start configuration"

defineArch
source ~/.zshenv || true

configureIos
configureFlutter

flutter doctor

successEcho "————> Success configuration!"
