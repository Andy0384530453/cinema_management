# TODO — Cinema management

Consolidation du travail restant. Plan exécuté sur la branche `feat/cinema-endpoints`
(créée depuis `preprod`).

## Backend

- [ ] **Domaine** — compléter le modèle UML :
  - Entité `Seat` (`idSeat` UUID, `number`, `room`)
  - Composition `Room.seats` (`1..1` contains `1..*`)
  - `Reservation.createdAt` + ensemble `Set<Seat> seats` (relation «books»)
- [ ] **Repositories** — `MovieRepository`, `SeatRepository`, `RoomRepository`,
      `UserRepository`, `ProjectionRepository`, `ReservationRepository`
      (package `repository/`, même style que `DummyRepository`)
- [ ] **Autorisation** — `AuthInterceptor` lisant `Authorization: Bearer <uuid>`,
      `RequestContext` (appelant courant en thread-local),
      `GlobalExceptionHandler` (`401` / `403` / `404`)
- [ ] **Services** — `MovieService`, `ReservationService`, `ProjectionService`
      (`@Transactional`)
- [ ] **Controllers** — `PUT /movies`, `GET /reservations`,
      `GET /reservationById?idReservation=`, `PUT /reservation`,
      `PUT /projection`, `GET /projections`

## Gates qualité

- [ ] Tests : matrice de rôles en WebMvcTest, unitaires services (Mockito),
      intégration FacadeIT, unitaires utilitaires → ≥ 80 % de couverture LIGNES
      (Jacoco)
- [ ] `format.bat` conforme google-java-format (job CI `format`)
- [ ] Historique de commits propre en conventional commits
      (feat:/fix:/test:/docs:, un changement logique par commit)

## Docs

- [ ] Réécrire `README.md` (aperçu projet + liens)
- [ ] `docs/DOMAIN.md` — entités, relations, enums
- [ ] `docs/API.md` — endpoints, rôles, auth, codes d'erreur
- [ ] `docs/TESTING.md` — exécution des tests, règle Jacoco, Docker
- [ ] `docs/GIT-CONVENTIONS.md` — politique de commits
- [ ] Mettre à jour `SPEC.md` et `doc/api.yaml` (les 6 endpoints)