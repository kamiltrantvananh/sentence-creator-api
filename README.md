# Sentence from words via API

## Task:
The system will be a simple webapp exposing REST API acting as a generator of the sentences from the words inserted to it by rules described below. You can input any word to the system but you need to specify its part of speech - NOUN or VERB or ADJECTIVE.

The system must be able to generate a sentence from the words according to the rule that sentence is in the form of NOUN VERB ADJECTIVE. Further description of resources functionality is available in the next part - API proposal.

We suggest you use Spring Boot or Dropwizard but it’s not mandatory. No persistence is needed -  persistent storage is only a bonus.

JSON format for API and library in unit tests:
  - https://github.com/lukas-krecan/JsonUnit
  
Integration tests:
  - https://github.com/jadler-mocking/jadler

If you find any nonsense in the task description or have a problem with bootstrapping the project in Spring Boot or Dropwizard feel free to ask at <jiri.sitina@gooddata.com>.

## Possible solution/API proposal
### Resources:

- [ ] GET /words
  - [ ] list all words added

- [ ] PUT /words/{word}
```
{
  "word": {
    "wordCategory": "NOUN/VERB/ADJECTIVE"
  }
}
```

- [ ] GET /words/{word}
  - [ ] optional: get list of forbidden words from file and reject them on API
```
{
  "word": {
    "word": "fooWord",
    "wordCategory": "NOUN/VERB/ADJECTIVE"
  }
}
```

- [ ] GET /sentences
  - list all generated sentences

- [ ] POST /sentences/generate (call without request body)
  - [ ] generate new sentence with unique id (accessible by GET /sentences/{sentenceID})
    - [ ] add date + time when the sentence was generated
    - [ ] optional 1. - number of views of the single sentence
    - [ ] on resource /sentences/{sentenceID}  you can see the number of view of the specific sentence
    - [ ] optional 2. (Advanced) Track number and id of exactly the same generated sentences (separate resource - introduce one)
  - [ ] sentence should be random String in form of NOUN VERB ADJECTIVE only from words added to system
    - [ ] if there is no all necessary types of words system should return error

- [ ] GET /sentences/{sentenceID}
```
{
  "sentence": {
    "showDisplayCount": 1,
    "text": "fooWord is nice"
  }
}
```

- [ ] GET /sentences/{sentenceID}/yodaTalk
  [ ] return special form of the requested sentence (ADJECTIVE NOUN VERB)
```
{
  "sentence": {
    "text": "fooWord nice is"
  }
}
```
