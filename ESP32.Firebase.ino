#include <WiFi.h>
#include <Firebase_ESP_Client.h>
#include <DHT.h>

// Constants
#define DHTTYPE DHT22
#define FIREBASE_AUTH "AIzaSyBdg8Z0FiyuP17ReXccwaTpZY-UxzGUYLw"
#define FIREBASE_HOST "https://smart-home-system-7cd5a-default-rtdb.firebaseio.com"
#define USER_EMAIL "esp32@gmail.com"
#define USER_PASSWORD "esp32_smart_home"

const char* ssid = "your SSID";
const char* password = "your wifi-password";

const int LED_PIN = 4;
const int DHT_PIN = 5;
const int FAN_PIN = 22;

// Firebase objects
FirebaseData fbdo;
FirebaseAuth auth;
FirebaseConfig config;

// DHT sensor
DHT dht(DHT_PIN, DHTTYPE);

void setup() {
    Serial.begin(115200);
    Serial2.begin(9600, SERIAL_8N1, 16, 17); // Serial2 for Arduino communication

    pinMode(LED_PIN, OUTPUT);
    pinMode(FAN_PIN, OUTPUT);

    // Connect to WiFi
    WiFi.begin(ssid, password);
    while (WiFi.status() != WL_CONNECTED) {
        delay(1000);
        Serial.println("Connecting to WiFi...");
    }
    Serial.println("\nWiFi connected!");

    // Initialize DHT sensor
    dht.begin();

    // Configure Firebase
    config.host = FIREBASE_HOST;
    config.signer.tokens.legacy_token = FIREBASE_AUTH;

    Firebase.begin(&config, &auth);
    Firebase.reconnectWiFi(true);
    Serial.println("Firebase connection established");

    // Turn the fan on for 4 seconds after booting
    digitalWrite(FAN_PIN, HIGH); // Turn fan on
    delay(4000); // Wait for 4 seconds
    digitalWrite(FAN_PIN, LOW); // Turn fan off
}

void loop() {
    if (!Firebase.ready()) return;

    // Get LED state from Firebase
    bool ledState;
    if (Firebase.RTDB.getBool(&fbdo, "/light", &ledState)) {
        Serial.print("LED state from Firebase: ");
        Serial.println(ledState);
        digitalWrite(LED_PIN, ledState);
    } else {
        Serial.println(fbdo.errorReason().c_str());
    }

    // Get fan state from Firebase
    bool fanState;
    if (Firebase.RTDB.getBool(&fbdo, "/Fan", &fanState)) {
        digitalWrite(FAN_PIN, fanState);
    } else {
        Serial.println(fbdo.errorReason().c_str());
    }

    // Get message from Firebase and send to Arduino
    String message;
    if (Firebase.RTDB.getString(&fbdo, "/message", &message)) {
        if (fbdo.dataType() == "string") {
            Serial.println("Received message: " + message);
            Serial2.println("Message: " + message); // Send message to Arduino
        }
    } else {
        Serial.println("Failed to get message: " + fbdo.errorReason());
        Serial2.println("Failed to send message from ESP32: " + fbdo.errorReason());
    }

    // Get temperature from DHT sensor and send to Firebase
    float temp = dht.readTemperature();
    if (isnan(temp)) {
        Serial.println("Failed to read from DHT sensor!");
    } else {
        Serial.print("Temperature: ");
        Serial.print(temp);
        Serial.println(" °C");
        if (Firebase.RTDB.setFloat(&fbdo, "/temperature", temp)) {
            Serial.println("Temperature sent to the cloud");
        } else {
            Serial.println(fbdo.errorReason().c_str());
        }
    }

    // Get password from Firebase
    String password;
    if (Firebase.RTDB.getString(&fbdo, "/password", &password)) {
        if (fbdo.dataType() == "string") {
            Serial.println("Received password: " + password);
        }
    } else {
        Serial.println("Failed to get password: " + fbdo.errorReason());
    }

    // Check for incoming password from Arduino
    if (Serial2.available() > 0) {
        String receivedPassword = Serial2.readStringUntil('\n');
        Serial.println("Received password from Arduino: " + receivedPassword);

        // Compare received password with stored password
        if (receivedPassword.equals(password)) {
            Serial.println("Password matched. Unlocking...");
        } else {
            Serial.println("Password mismatch. Access denied.");
        }
    }
}
