# SPEC — Endpoints Cinema Management

## 1. PUT /movies

Créer ou mettre à jour un film.

| Rôle | Résultat |
|------|----------|
| CLIENT | 403 FORBIDDEN |
| EMPLOYEE | 403 FORBIDDEN |
| MANAGER | 200 OK |

**Request body (JSON) :**

```json
{
  "idMovie": "UUID",
  "title": "String",
  "genre": "THRILLER | ROMANCE | COMEDY | DRAMA | ACTION | SCI-FI | FANTASY | ANIMATION",
  "description": "String",
  "duration": "Duration (ex: PT2H5M)"
}
```

**Réponses :**
- `200 OK` — film créé/mis à jour
- `403 FORBIDDEN` — utilisateur non MANAGER

## 2. GET /reservations

Lister toutes les réservations.

| Rôle | Résultat |
|------|----------|
| CLIENT | 403 FORBIDDEN |
| EMPLOYEE | 200 OK |
| MANAGER | 200 OK |

**Réponses :**
- `200 OK` — liste des réservations (idReservation, user, projection)
- `403 FORBIDDEN` — utilisateur CLIENT

## 3. GET /reservationById

Récupérer une réservation par son id.

Paramètre : `idReservation` (UUID)

| Rôle | Résultat |
|------|----------|
| CLIENT (propriétaire de la réservation) | 200 OK |
| CLIENT (réservation d'un autre client) | 403 FORBIDDEN |
| EMPLOYEE | 200 OK |
| MANAGER | 200 OK |

**Réponses :**
- `200 OK` — réservation trouvée (idReservation, user, projection)
- `403 FORBIDDEN` — CLIENT non propriétaire de la réservation
- `404 NOT FOUND` — réservation inexistante
