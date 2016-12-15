package me

import groovy.transform.TupleConstructor

@TupleConstructor(includes = "candidate")
class CandidateScore {
    int candidate
    int score
}
