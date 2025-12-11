# CMG Travels – Android Ticket Booking & Real-Time Bus Tracking App

CMG Travels is a modern **Android-based ticket booking and bus tracking application** built using **Kotlin**.  
It allows users to book tickets, track buses in real time, check schedules, and manage their travel information — all inside one streamlined mobile app.

This project was built as part of my personal learning in **Android development, Firebase, and real-time geolocation systems**.

---

## 🚀 Features

### 🎫 Ticket Booking System
- Search available buses by route  
- Select seats & book directly from the app  
- Ticket details stored securely in Firebase  
- Auto-generated ticket ID for each booking  

### 🗺️ Real-Time Bus Tracking
- Live location updates using **Google Maps SDK**
- Bus driver app continuously sends location updates
- Users can see the **moving bus marker** on the map  
- Smooth location refresh and optimized performance  

### 🔐 Authentication & User System
- Firebase Authentication  
- Email/Password login  
- User-specific tickets & travel history  

### 📅 Schedule & Route Management
- View bus routes  
- Live updates pushed from Firebase  
- Easy route overview with map visualization  

### 💬 In-App Chat (Personal Assistant Style)
A simple built-in chat interface that supports:
- Text messaging  
- Location sharing  
- A small utility section (notes, calculator, etc.)  

### ☁ Backend & Database
- Entirely **Firebase-based backend**  
- Realtime Database for location tracking  
- Firestore for storing user/bookings  
- Firebase Storage for media  

---

## 📱 Tech Stack

**Language:**  
- Kotlin  

**Android Libraries:**  
- XML Layouts  
- RecyclerView  
- ViewModel & LiveData  
- Coroutines  

**Maps & Location:**  
- Google Maps SDK  
- FusedLocationProvider API  

**Backend:**  
- Firebase Authentication  
- Firebase Firestore  
- Firebase Realtime Database  
- Firebase Storage  

---

## 📸 Screenshots (Add later)
You can add app screenshots here:


## 🏗️ Architecture
- MVVM architecture  
- Repository pattern  
- Realtime listeners for maps  

---

## ⚙️ Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/COOLMishraG/CMG-Travels.git
   Open the project in Android Studio

2. Open the project in Android Studio:

3. Add your Firebase configuration:
    - Download google-services.json from Firebase Console
    - Place it inside app/

4. Enable the required APIs:
    - Google Maps SDK
    - Location Services
5. Build & run the app on your device

---

## 🙋 About the Developer

Built with ❤️ by Anuj Mishra — Android & Backend Developer

📍 Indore, India

Email: anujmishra04@outlook.com

GitHub: https://github.com/COOLMishraG

LinkedIn: https://linkedin.com/in/anuj-omishra-
