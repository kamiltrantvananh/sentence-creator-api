# Sentence from words via API

## Task
The system will be a simple webapp exposing REST API acting as a generator of the sentences from the words inserted to 
it by rules described below. You can input any word to the system but you need to specify its part of speech - NOUN or VERB or ADJECTIVE.

The system must be able to generate a sentence from the words according to the rule that sentence is in the form of NOUN 
VERB ADJECTIVE. Further description of resources functionality is available in the next part - API proposal.

We suggest you use Spring Boot or Dropwizard but it’s not mandatory. No persistence is needed -  persistent storage is 
only a bonus.

JSON format for API and library in unit tests:
  - https://github.com/lukas-krecan/JsonUnit
  
Integration tests:
  - https://github.com/jadler-mocking/jadler

If you find any nonsense in the task description or have a problem with bootstrapping the project in Spring Boot or 
Dropwizard feel free to ask at <jiri.sitina@gooddata.com>.

## Description
*Sentence Creator Api* is an REST API (Spring Boot Application) that's create sentences from words. 
It is possible to define file path with forbidden words in "application.properties" as property . 
 - Example file _forbidden-words.json_
```
["nothing", "noone", "coward"]
```

## Run application
1. Clone/download this repository
2. Build with maven
```
mvn clean install
```
3. Run with java
```
java -jar target/sentence-creator-api.0.0.1.jar
```
4. Go to http://localhost:8080/swagger-ui.html#
5. Enjoy

## Resources
### Words
#### GET "/words"
 - list of all words added without forbidden words
 - response:
```
[
  {
    "word": {
      "wordCategory": "NOUN/VERB/ADJECTIVE"
    }
  },
  {
  "word": {
    "wordCategory": "NOUN/VERB/ADJECTIVE"
  }
}
]
```

#### PUT "/words/{word}"
 - add word with 
 - request:
  - word (path param): text format of word
  - request body:
```
{
  "word": {
    "wordCategory": "NOUN/VERB/ADJECTIVE"
  }
}
```
#### GET "/words/{word}/{wordCategory}"
   - get word by its text and category
   - request:
    - word (path param): text format of word
    - wordCategory (path param): NOUN/VERB/ADJECTIVE
   - response:
```
{
  "word": {
    "word": "string",
    "wordCategory": "NOUN/VERB/ADJECTIVE"
  }
}
```

#### GET "/words/{word}"
   - get first word (priority by category NOUN > VERB > ADJECTIVE) by its text
   - request:
    - word (path param): text format of word
   - response:
```
{
  "word": {
    "word": "string",
    "wordCategory": "NOUN/VERB/ADJECTIVE"
  }
}
```
#### DELETE "/words/"
  - remove all words

#### DELETE "/words/{word}"
  - remove first word (priority by category NOUN > VERB > ADJECTIVE) by its text
  - request:
    - word (path param): text format of word

#### DELETE "/words/{word}/{wordCategory}"
  - remove word by its text and category
  - request:
    - word (path param): text format of word
    - wordCategory (path param): word category

## Sentences
#### GET "/sentences"
  - list of all generated sentences
  - response:
```
[
  {
    "sentence": {
      "created": "YYYY-MM-DDThh:mm:ss.SSS'Z",
      "id": 0,
      "showDisplayedCount": 1,
      "text": "noun verb adjective"
    }
  },
  {
    "sentence": {
      "created": "YYYY-MM-DDThh:mm:ss.SSS'Z",
      "id": 1,
      "showDisplayedCount": 1,
      "text": "noun verb adjective"
    }
  }
]
```

#### POST "/sentences/generate"
   - generate new sentence with unique id (accessible by GET /sentences/{sentenceID})
   - request: empty
   - response:
```
{
  "sentence": {
    "created": "YYYY-MM-DDThh:mm:ss.SSS'Z",
    "id": 0,
    "showDisplayedCount": 1,
    "text": "noun verb adjective"
  }
 }
```
#### GET "/sentences/{sentenceID}"
  - get sentence by its ID
  - request:
    - sentenceId (path param): ID of sentence
  - response:
```
{
  "sentence": {
    "created": "YYYY-MM-DDThh:mm:ss.SSS'Z",
    "id": 0,
    "showDisplayedCount": 1,
    "text": "noun verb adjective"
  }
}
```

#### GET "/sentences/{sentenceID}/yodaTalk"
   - return special form of the requested sentence (ADJECTIVE NOUN VERB)
   - response:
```
{
  "sentence": {
    "created": "YYYY-MM-DDThh:mm:ss.SSS'Z",
    "id": 0,
    "showDisplayedCount": 1,
    "text": "adjective noun verb"
  }
}
```

#### DELETE "/sentences/"
  - remove all sentences
  
#### DELETE "/sentences/{sentenceID}"
  - remove sentence by its ID
  - request:
    - sentenceID (path param): sentence ID
  
#### GET "/sentences/stats"
  - get stats of duplicated sentences
  - response:
```
[
  {
    "sentenceIds": [0,1,2],
    "text": "noun verb adjective"
  },
  {
    "sentenceIds": [3],
    "text": "noun verb adjective"
  }
]
```
