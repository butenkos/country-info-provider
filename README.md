# country-info-provider
## NOTE: unfortunately, at the moment the remote web service (data source) is not working, so the solution cannot be tested
### How to build and run
Project is compiled and packed as an executable jar. To build the app from the source code the run maven command (mvn package or mvn package -DskipTests)<br/>
To run the app run `java -jar country-info-provider-demo.jar` <br/>
The application is running on `8080` port
### How to use
`http://localhost:8080/country` - call to `/country` without query params returns list of three countries with the highest population<br/>
to get the list of three countries with the lowest population append the following query param 'sort=population' like the following:<br/>
`http://localhost:8080/country?sort=population` <br/>
<br/>

`http://localhost:8080/country` is the same as `http://localhost:8080/country?sort=-population&limit=3` </br>
#### Available query parameters
o `sort` - multiple sort parameters can be specified separated by comma, for example: `sort=population,country_name`. Three sorting fields are available in current implementation: `country_name`, `country_code`, `population`. <br/>Default sorting order is ascending. In order to sort in descending order use `-` prefix like this:<br/> `sort=-population,country_name` (here results will be sorted by population in descending order and by country_name in ascending order)<br/>
o `limit` - defines the number of countries to be displayed. If not specified, default pre-configured value is used (3).
