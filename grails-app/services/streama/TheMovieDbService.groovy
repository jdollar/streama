package streama

import groovy.json.JsonSlurper
import grails.transaction.Transactional

@Transactional
class TheMovieDbService {

  def BASE_URL = "https://api.themoviedb.org/3"
  def EMPTY_JSON = '{}'

  def getAPI_KEY(){
    return Settings.findBySettingsKey('TheMovieDB API key')?.value
  }


  def validateApiKey(apiKey){
    def JsonContent = apiKey?.trim() ? new URL(BASE_URL + '/configuration?api_key=' + apiKey).text : EMPTY_JSON
    return new JsonSlurper().parseText(JsonContent)
  }


  def getSimilarMovies(movieId){
    def JsonContent = movieId?.trim() ? new URL(BASE_URL + "/movie/$movieId/similar?api_key=$API_KEY").text : EMPTY_JSON
    return new JsonSlurper().parseText(JsonContent)
  }


  def getExternalLinks(showId){
    def JsonContent = showId?.trim() ? new URL(BASE_URL + "/tv/$showId/external_ids?api_key=$API_KEY").text : EMPTY_JSON
    return new JsonSlurper().parseText(JsonContent)
  }

  def getMovieGenres(){
    def JsonContent = new URL(BASE_URL + "/genre/movie/list?api_key=$API_KEY").text
    def genres =  new JsonSlurper().parseText(JsonContent).genres

    genres?.each{ genre ->
      genre["apiId"] = genre.id
      genre.id = null
    }

    return genres
  }

  def getTvGenres(){
    def JsonContent = new URL(BASE_URL + "/genre/tv/list?api_key=$API_KEY").text
    def genres =  new JsonSlurper().parseText(JsonContent).genres

    genres?.each{ genre ->
      genre["apiId"] = genre.id
      genre.id = null
    }

    return genres
  }

  def getFullMovieMeta(movieId){
    def JsonContent = movieId?.trim() ? new URL(BASE_URL + "/movie/$movieId?api_key=$API_KEY").text : EMPTY_JSON
    return new JsonSlurper().parseText(JsonContent)
  }
}
