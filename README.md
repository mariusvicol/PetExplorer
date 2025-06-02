<h1>
  <span style="font-size: 2rem;">🐾 Pet Explorer</span>
  <span style="font-size: 1.2rem; font-style: italic;">– Client Java al aplicației destinate tuturor deținătorilor de animale de companie</span>
</h1>

## 📌 Descriere:
**Pet Explorer** este o aplicație Android creată pentru a ajuta deținătorii de animale de companie 
să găsească rapid locații utile (farmacii veterinare, parcuri, saloane, etc.) și să primească notificări 
în timp real în cazul pierderii sau regăsirii unui animal.

Aplicația comunică cu un server Java (Spring Boot) prin REST API-uri și WebSocket, oferind o experiență
**modernă, intuitivă și reactivă**.

---

## 🛠️ Tehnologii folosite

- **Java** – limbaj de programare principal
- **Android Studio** – IDE pentru dezvoltarea aplicației
- **Retrofit2** – pentru comunicare REST (GET/POST/PUT/DELETE)
- **OkHttp** – interceptor pentru debugging și logare
- **Gson** – conversie JSON în obiecte
- **Google Maps SDK** – afișarea hărții cu locații
- **STOMP over WebSocket** – notificări în timp real

---

## 🔌 Comunicare cu backendul

### 🔁 REST API (Retrofit)

Aplicația comunică cu serverul prin API-uri expuse la: `https://<host>:<port>/api/`


Exemple:

| Funcție                 | Endpoint                     | Metodă HTTP |
|-------------------------|------------------------------|-------------|
| Login                   | `/api/users/login`           | POST        |
| Înregistrare utilizator | `/api/users/register`        | POST        |
| Lista farmacii          | `/api/farmacii`              | GET         |
| Adăugare animal pierdut | `/api/animale-pierdute`      | POST        |
| Marcarea ca găsit       | `/api/animale-pierdute/{id}` | PUT         |

### 🔔 WebSocket (STOMP)

Clientul Android se abonează la următoarele topicuri:

| Topic                              | Descriere                                |
|------------------------------------|------------------------------------------|
| `/topic/animale-pierdute`          | notificare când cineva adaugă un animal  |
| `/topic/animale-pierdute/resolved` | notificare când un animal e marcat găsit |
| `/user/queue/notifications`        | notificare personalizată pentru un user  |

---

## 🗺️ Funcționalități principale

- ✅ Autentificare / Înregistrare utilizatori
- 🗺️ Afișare locații utile pe hartă (Google Maps)
- 🔍 Căutare după numele locației
- 🐾 Adăugare animal pierdut cu poză, descriere și locație
- 🔔 Primirea de **notificări live** când apar animale pierdute sau găsite
- 📥 Salvare automată a sesiunii
- 🌙 Dark Mode
- 📌 Adăugare și ștergere locații favorite
