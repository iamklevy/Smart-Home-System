#include <LiquidCrystal.h>
#include <SoftwareSerial.h>
#include <Keypad.h>

// SoftwareSerial object for communication
SoftwareSerial mySerial(0, 1); // RX, TX 

// LCD configuration
LiquidCrystal lcd(8, 9, 2, 3, 4, 5);

// Ultrasonic sensor pins
const int trigPin = A2;
const int echoPin = A3;

// Keypad configuration
const byte ROWS = 4;
const byte COLS = 4;
char keys[ROWS][COLS] = {
  { '1', '2', '3', 'A' },
  { '4', '5', '6', 'B' },
  { '7', '8', '9', 'C' },
  { '*', '0', '#', 'D' }
};
byte rowPins[ROWS] = { 13, 12, 11, 10 };
byte colPins[COLS] = { A5, A4, 7, 6 };

// Create the Keypad object
Keypad keypad = Keypad(makeKeymap(keys), rowPins, colPins, ROWS, COLS);

String receivedPassword;
String lcdMessage;

void setup() {
  // Initialize serial communication
  Serial.begin(9600);
  mySerial.begin(9600); // ESP32 communication

  // Initialize the LCD
  lcd.begin(16, 2);
  lcd.print("Smart Home T8");

  // Configure ultrasonic sensor pins
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);

  // Initial delay for startup
  delay(8000);

  // Clear LCD and wait for initial messages
  lcd.clear();
}

void loop() {
  static char messageBuffer[7];
  static int messageIndex = 0;
  char key = keypad.getKey();

  // Check if a key is pressed
  if (key) {
    Serial.print("Key Pressed: ");
    Serial.println(key);
    lcd.print(key);

    // Store the key in the buffer
    if (messageIndex < 6) {
      messageBuffer[messageIndex++] = key;
      messageBuffer[messageIndex] = '\0'; // Null terminator
    }

    // If buffer is full, reset the index
    if (messageIndex == 6) {
      messageIndex = 0; // Reset index for next message
    }
  }

  // Measure the distance using the ultrasonic sensor
  long duration, distance;

  // Send a pulse to the trigPin
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);

  // Read the echoPin and calculate the distance
  duration = pulseIn(echoPin, HIGH);
  distance = duration * 0.034 / 2;

  // Display the distance on the LCD
  lcd.setCursor(0, 1);
  lcd.print("Distance: ");
  lcd.print(distance);
  lcd.print(" cm    ");  // Add spaces to clear any leftover character
  delay(1000);

  // Check for incoming messages from ESP32
  if (mySerial.available() > 0) {
    String message = mySerial.readStringUntil('\n'); // Read the message from ESP32
    Serial.println("Received Message from ESP32: " + message);

    // Check if the message starts with "PASSWORD:" or "MESSAGE:"
    if (message.startsWith("PASSWORD:")) {
      receivedPassword = message.substring(9); // Get the password
      Serial.println("Password received: " + receivedPassword);
    } else if (message.startsWith("MESSAGE:")) {
      lcdMessage = message.substring(8); // Get the LCD message
      Serial.println("LCD Message received: " + lcdMessage);
      
      lcd.clear();
      lcd.setCursor(0, 0);
      lcd.print(lcdMessage);
    }
  }

  // Small delay to stabilize the readings
  delay(100);
}
