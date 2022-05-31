# cine

This application was generated using JHipster 6.9.1, you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v6.9.1](https://www.jhipster.tech/documentation-archive/v6.9.1).

REST API to manage a cinema. It implements CRUD operation on movies, proyections, theater and theater seats

## Entities

1. Pelicula - movie
3. Proyeccion - proyection
4. Sala - theater
5. Butaca - sold theater seat

## Reportes
The api has an endpoint to get reports.
1. GET /reportes/butacas_vendidas/{inicio}/{fin} - returns all the Butacas sold between dates {inicio} and {fin}
2. GET /reportes/butacas_vendidas/{id_proyeccion}/{inicio}/{fin} - returns all the Butacas sold between dates {inicio} and {fin} of a given Proyeccion {id_proyeccion}
3. GET /reportes/masvendidas/{inicio}/{fin} - returns a top 5 of the Proyeccion with more Butacas sold
4. GET /reportes/butacas_vendidas - returns all the sold Butacas of active Peliculas

## Endpoints
### Pelicula
1. POST /peliculas - creates a Pelicula (movie)
2. GET /pelicula - gets all Peliculas
3. PUT /pelicula - updates Pelicula
4. DELETE /pelicula/{id}
5. GET /peliculas/{inicio}/{fin} - gets all Peliculas between dates inicio and fin
6. GET /peliculas/{id}/{inicio}/{fin} - returns the list of Proyeccions with this movie

### Proyeccion
1. POST /proyeccions
2. GET /proyeccions - gets all Proyeccion
3. PUT /proyeccions
4. DELETE /proyeccions/{id}
5. GET /proyeccions/hoy
6. GET /proyeccions/{inicio}/{fin} - gets all Proyeccion between dates inicio and fin
7. GET /proyeccions/pelicula/{id} - gets Proyeccion of Pelicula id
8. GET /proyeccions/pelicula/{id}/fecha/{fecha} - gets list of Proyeccion of Pelicula {id} in date {fecha}

### Salas
1. POST /salas
2. GET /salas
3. PUT /salas
4. DELETE /salas/{}
5. GET /salas/{id}

### Butacas
1. POST /butacas
2. GET /butacas
3. PUT /butacas
5. GET /butacas/{}
DELETE is not allowed.

