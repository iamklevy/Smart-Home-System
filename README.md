# Smart-Home-System

This project aims to develop a remote smart control system for managing embedded electronic circuits using a mobile application. By leveraging seamless connectivity, remote control, and real-time monitoring, users can control various smart home features such as temperature, lighting, and security. The system integrates microcontrollers, wireless modules, and sensors, utilizing Firebase for cloud data storage and SQLite for local caching. Developed with Android Studio, the mobile application ensures robust interaction with the embedded system, providing a comprehensive solution for smart home management.

  <img src = "https://github.com/user-attachments/assets/0da1f0e6-30cb-4b59-a000-539721b897e0" width="2100" height="500"/>
  <img src = "https://github.com/user-attachments/assets/67149ade-c550-4458-866f-011296cffb4a" width="2100" height="500"/>

  <p>
    <img src="https://github.com/iamklevy/Smart-Home-System/assets/94145850/5672c042-2992-4b07-9b61-9b5554ca63a9"  width="500"/>
    <img src="https://github.com/iamklevy/Smart-Home-System/assets/94145850/1a5978e2-9941-4cbd-ab57-d2040edd4809"  width="500"/>
    <video src="https://github.com/iamklevy/Smart-Home-System/assets/94145850/cd08469e-bd11-4a1f-84ab-41c220ad1fc4" />
      Your display does not support the video tag. open browser to see the video.
  </p>

  <p>
    <img src="https://github.com/iamklevy/Smart-Home-System/assets/94145850/b79a3f3a-f2e6-49dd-8464-6641ca4a69e2"  width="500"/>
    <img src="https://github.com/iamklevy/Smart-Home-System/assets/94145850/865ebd61-d0e3-4f13-b5e6-48d7d82bb3c4" width= "500"/>
  </p>
  <p>
    <img src="https://github.com/iamklevy/Smart-Home-System/assets/94145850/48579004-a3dd-4fc0-b7d3-cd1535bc90ab"  width="500"/>
    <img src="https://github.com/iamklevy/Smart-Home-System/assets/94145850/20e3a5c7-1ae9-428d-8c67-ff8a34f011c7"  width="500"/>
  </p>
  <p>
    <a href="https://www.youtube.com/watch?v=LAhoLC1QU5o" target="_blank">
      <img src="https://img.youtube.com/vi/LAhoLC1QU5o/0.jpg" width="500" />
    </a>
  </p>
      
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
  - Temperature Sensor (DH22)
  - Ultrasonic Sensor
  - fan motor
  - LCD
  - Keypad
- **Power Management**: Disconnect circuits to conserve power when network is disconnected.

### Integration
- **Firebase and ESP32**: Firebase sends data to ESP32, which communicates via serial with Arduino for LCD display and password verification.
- **Network Disconnection**: Display a toast message "Please Check your Network Connection!" on the mobile app and disconnect hardware components to save power.














