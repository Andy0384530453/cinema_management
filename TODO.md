# TODO — Cinema management

Consolidation du travail restant. Le refactor entités JPA / modèles et le domaine
métier sont réalisés sur `preprod`.

## Architecture — séparation entités JPA / modèles ✅

- [x] **Entités JPA préfixées `J*`** dans `entity/` : `JMovie`, `JUser`, `JRoom`,
      `JProjection`, `JReservation` (annotations JPA, tables inchangées)
- [x] **Modèles de domaine purs** dans `model/` : `Movie`, `User`, `Room`,
      `Projection`, `Reservation` (POJO Lombok, sans annotation JPA)
- [x] **Enums `Genre` et `UserRole` déplacées** de `entity/enums` vers `model/`
- [x] **Mappers** : `MovieMapper` (modèle <-> DTO) et `JMovieMapper` (JPA <-> modèle)
- [x] **Service** `MovieService` : travaille en domaine, persiste via `JMovieMapper`
- [x] **Repository** `MovieRepository` → `JpaRepository<JMovie, UUID>`
- [x] **Tests mis à jour** : tests de modèles (`model/*Test`), `JMovieMapperTest`,
      `MovieMapperTest`, `MovieServiceTest`, imports des enums dans les tests

## Backend — fonctionnalités

- [x] `PUT /movies` avec contrôle de rôle (`X-User-Role`, `SecurityService`)
- [x] **Domaine** — compléter le modèle UML :
  - Entité `Seat` (`idSeat` UUID, `number`, `room`)
  - Composition `Room.seats` (`1..1` contains `1..*`)
  - `Reservation.createdAt` + ensemble `Set<Seat> seats` (relation «books»)
- [x] **Repositories** — `SeatRepository`, `RoomRepository`,
      `UserRepository`, `ProjectionRepository`, `ReservationRepository`
      (package `repository/`, même style que `DummyRepository`)
- [x] **Mappers J* <-> modèles** pour `User`, `Room`, `Projection`, `Seat`,
      `Reservation` (même pattern que `JMovieMapper`)
- [ ] **Autorisation** — `AuthInterceptor` lisant `Authorization: Bearer <uuid>`,
      `RequestContext` (appelant courant en thread-local),
      `GlobalExceptionHandler` (`401` / `403` / `404`)
- [ ] **Services** — `ProjectionService` (`@Transactional`), écriture `ReservationService`
- [ ] **Controllers** — `PUT /reservation`, `PUT /projection`, `GET /projections`

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