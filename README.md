# Number Verify Starter Android - Quickstart Example

## Overview

This quickstart repository demonstrates how to integrate and use the **Glide Number Verify SDK** in an Android app. 

## Prerequisites

Before you can run this example app, ensure you have the following:
- Android Studio 
- Android SDK 34 or higher
- A physical Android device with an active mobile data connection (recommended for testing)
- **Glide Client ID** ([registration guide](https://docs.gateway-x.io/getting-started/registration))
- **Backend Server URL**: You can deploy the backend by following our [Number Verify Web Quickstart](https://docs.gateway-x.io/number-verify/quickstart), which includes backend deployment instructions.

## Getting Started

### 1. Clone the Repository

First, clone the repository to your local machine:

```bash
git clone https://github.com/GlideApis/number-verify-starter-android.git
```

### 2. Open the Project in Android Studio

Open Android Studio and select "Open an Existing Project." Navigate to the cloned repository and open it.

### 3. Configure Your Environment Variables

To run the app, you need to provide your **Backend Server URL** and **Glide Client ID**. Follow these steps:

- Open the `local.properties` file located in the root of the project.
- Add your environment variables as follows:

```properties
BACKEND_SERVER_URL=https://your-backend-url.com
GLIDE_CLIENT_ID=your_glide_client_id_here
```

### 4. Sync Gradle Files

Make sure to sync your Gradle files after adding the environment variables. You can do this by clicking "Sync Now" in the notification bar in Android Studio.

## Running the App

### 1. Build the App

Once your environment is set up, you can build the app by selecting **Build > Make Project** or by clicking the green hammer icon.

### 2. Run the APP

If you prefer you can first run the app using the emulator with our test number input:
`+555123456789`

To run the app on your device to showcae more realistic usecase with cellular data:
- Connect your Android device via USB.
- Ensure **Developer Options** and **USB Debugging** are enabled on your device
- Make sure correct device (or emulator) is selected in the topbar
- Click the "Run" button (green triangle) in Android Studio or press.

### 3. Verify Number Example

The app will start and automatically initiate the number verification process using the Number Verify SDK. This will display the phone number input box and after it will run the verification flow as per your configuration.

The example app provides callbacks for success and failure scenarios, which will be logged in the app's UI and logcat for easy debugging.


## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more information.
