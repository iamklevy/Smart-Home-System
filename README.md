# Smart-Home-System

<details>
  <summary>Android App</summary>
  <p>
    <img src="https://github.com/iamklevy/Smart-Home-System/assets/94145850/c8e38a41-60e2-4af9-be84-1dd9f95f6b57" alt="login" width="500"/>
    <img src="https://github.com/iamklevy/Smart-Home-System/assets/94145850/ef1975bc-b831-4da1-9c7f-6c49f0d7bc4b" alt="sign up" width="500"/>
  </p>
  <p>
    <img src="https://github.com/iamklevy/Smart-Home-System/assets/94145850/cec0a738-4832-4021-b9fa-42be5966104e" alt="home page" width="500"/>
    <img src="https://github.com/iamklevy/Smart-Home-System/assets/94145850/33741c66-ee4f-4ded-98bc-6bf56515a504" alt="settings" width="500"/>
  </p>
  <p>
    <img src="https://github.com/iamklevy/Smart-Home-System/assets/94145850/ed9e8dbc-5858-45e1-bb00-56ce7bb499d4" alt="App logs" width="500"/>
  </p>
</details>

<details>
  <summary>3D Model</summary>
  <p>
    <img src="https://github.com/iamklevy/Smart-Home-System/assets/94145850/5672c042-2992-4b07-9b61-9b5554ca63a9"  width="500"/>
    <img src="https://github.com/iamklevy/Smart-Home-System/assets/94145850/1a5978e2-9941-4cbd-ab57-d2040edd4809"  width="500"/>
    <video src="https://github.com/iamklevy/Smart-Home-System/assets/94145850/cd08469e-bd11-4a1f-84ab-41c220ad1fc4" />
  </p>
</details>

<details>
  <summary>Maquette</summary>
  <p>
    <img src="https://github.com/iamklevy/Smart-Home-System/assets/94145850/b79a3f3a-f2e6-49dd-8464-6641ca4a69e2"  width="500"/>
    <img src="https://github.com/iamklevy/Smart-Home-System/assets/94145850/865ebd61-d0e3-4f13-b5e6-48d7d82bb3c4" width= "500"/>
  </p>
  <p>
    <img src="https://github.com/iamklevy/Smart-Home-System/assets/94145850/48579004-a3dd-4fc0-b7d3-cd1535bc90ab"  width="500"/>
    <img src="https://github.com/iamklevy/Smart-Home-System/assets/94145850/20e3a5c7-1ae9-428d-8c67-ff8a34f011c7"  width="500"/>

</details>

## Pervasive Computing Project: Remote Smart Control

### Project Overview
Develop a solution for remote smart control to manage embedded electronic circuits using a mobile application. The main objectives include:
- Seamless Connectivity
- Remote Control
- Real-time Monitoring

### System Description
- **Components**: Microcontroller, wireless integrated module, environmental sensors.
- **Connectivity**: Over Wi-Fi, exchanging data between the microcontroller and mobile application.
- **Data Storage**: Firebase (cloud) and SQLite (cache).

### Embedded System: Smart Home System
1. **Temperature Control**
   - Measure room temperature via a temperature sensor.
   - Upload temperature data to Firebase.
   - View temperature in a mobile app activity.

2. **Home Lock (Password)**
   - Create and store a home password through the mobile app.
   - Microcontroller checks the password via connected keypad for door lock control.

3. **Light Control**
   - Send a Boolean value from the mobile app to the microcontroller to control an LED.
   - Store the value on Firebase.

4. **Fan Control**
   - Send a Boolean value from the mobile app to the microcontroller to control a fan motor.
   - Store the value on Firebase.

5. **Entry Attack Alert**
   - Detect entry using an ultrasonic sensor.
   - Set an "alert" variable on Firebase if an intrusion is detected.
   - Microcontroller listens for the "alert" variable change and sends a notification to the Android app.
   - Display "We are Safe" or "We are at Risk!" message on the app.
   - Reset "alert" to false upon correct password entry.

6. **Message Display**
   - Enter a message in the mobile app.
   - Store the message on Firebase.
   - Display the message on an LCD via the microcontroller.

### Software Requirements
1. **Mobile Application (Android Studio IDE)**
   - **Registration Activity**: Collect user details (name, username, password, profile picture, email, birthdate) and store on Firebase, cache in SQLite.
   - **Login Activity**: 
     - Login using normal method or Firebase authentication.
     - Implementation of "Remember Me" using a checkbox.
     - Implementation "Forgot Password" feature.
   - **Main Activity (Home Activity)**: Display a list of actions.
     - **ListView/RecyclerView**: Contains items for each action (image/title), navigate to action activity on click.
     - **Search Facility**: Filter actions by title via search bar.
   - **Options Menu**:
     - **Activity Log**: Navigate to Activity Log activity.
     - **Profile**: Navigate to Profile Activity.
     - **Logout**: Return to login form.
   - **Activity Log**: Storing actions with timestamps on Firebase, cache using SQLite.
   - **Profile Activity**: Displaying user profile picture, username, and logout button.
   - **Action Activities**: Implementation of individual activities for each action (e.g., Temperature_Activity).

### Hardware used
- **Common Components**: Breadboard, jump wires, LEDs, ESP8266 NodeMCU (or ArduinoUno + ESP8266 Wi-Fi Module).
- **Application Specific Components**:
  - Temperature Sensor (LM35)
  - Ultrasonic Sensor
  - fan motor
  - LCD
  - Keypad
- **Power Management**: Disconnect circuits to conserve power when network is disconnected.

### Integration
- **Firebase and ESP32**: Firebase sends data to ESP32, which communicates via serial with Arduino for LCD display and password verification.
- **Network Disconnection**: Display a toast message "Please Check your Network Connection!" on the mobile app and disconnect hardware components to save power.














