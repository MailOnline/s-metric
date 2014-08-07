# s-metric

s-metric is a set of metrics to score how close a string compare to another. It applies to all problems related to text similarity, including suggestions for mispelled words and many others. The current metrics are just three (Hamming distance with padding, naive brute force and levenshtein) or a combination of them. It can be used as a library or as a command line tool. Please make sure you're using Java 1.7 (or you will need an additional fork-join package in the JRE boostrap libraries).

## Command line tool

* Useful to use in shell scripting or quick string comparisons
* To create the uberjar: please make sure you have leiningen installed, then lein uberjar at the project root after git cloning the project.
* It will print the following options:

        "-l" "--levenshtein" "use the http://en.wikipedia.org/wiki/Levenshtein_distance"
        "-h" "--hamming" "use http://en.wikipedia.org/wiki/Hamming_distance with padding converted to a score over the max total"
        "-n" "--naive" "Use naive matching, comparing all combinations of contigous substrings"
        "-a" "--all" "Combine all distance metrics together and output the total match score"
        "-v" "--verbose" "Add detailed output"
        "-t" "--test" "just echoes the argument list and exits"

* By default it defaults to combined metrics
* Use with 

        java -jar target/s-metric.jar beleive believe

* It should print: Levenshtein Score: 71.43%

## How to use as a library

Add:

        [s-metric "0.0.4"]

to your project.clj. Here's an example snippet some interesting feature:

```clojure
    (ns s-metric.core-test 
      (:require [s-metric.core :as metric]
                [s-metric.bulk :as bulk]))

    ;;;; AUTOCORRECTION

    ;; A simple dictionary (in reality this would be something
    ;; like the list of commonly mispelled words)
    (def dictionary ["balance"
                     "battalion"
                     "beginning"
                     "belief"
                     "believe"
                     "beneficial"
                     "benefited"
                     "boundaries"
                     "Britain"
                     "business"])

    ;; returns the score and the best match in the dictionary, 
    ;; such as [71.43 "believe"]
    (bulk/mispelled "beleive" dictionary)

    ;; if you need more control you can access the top 20 scores
    ;; ordered from the lowest to the highest
    (bulk/top-scores "buisness" dictionary)


    ;;;; SINGLE SCORE


    ;; naive works better for substrings "affinity"
    ;; => 66.07
    (metric/naive "ACACACTA" "AGCACACA")

    ;; hamming is more about editing distance
    ;; and performs lower on DNA-like sequencing
    ;; => 25.0
    (metric/hamming "ACACACTA" "AGCACACA")

    ;; levenshtein also considers deletion and insertions
    ;; so it performs good with substrings as well
    ;; => 75.0
    (metric/levenshtein "ACACACTA" "AGCACACA")
```

## Metrics

The *Levenshtein Distance* is one of the most popular distance metrics. It offers the possiblity to calculate the best match for a set of possible transformations all bringing to the same result, returning the one with the minimum set of step to transform a string into another, i.e. their minimum distance.

The *Hamming distance* algorithm presented here is an adaptation of the original definition which applies to string of the same length. If strings are not of the same length, the shorter one is padded to the left with spaces and then then compared. Hamming returns exactly the same results as Levenshtein applied to strings of the same length, but is simpler and faster. Use it instead of Levenshtein in this case.

The *Naive score* approach splits both strings into increasingly bigger contigous substrings, starting from pairs. It collects scores for each matching slice. Use naive scoring when you are interested in comparing substrings more than editing distance. It explodes in a O(n-1)^2 complexity in the worst scenario (n is the length of both strings), so use with relatively small strings.

The *Combined metric* allows for free combination of the available metrics averaging the results. The default combined metrics for the command line combines them all. If you use s-metric as a library you can mix and match them plus adding your own.

## How to run the tests

Tests are covering all useful scenarios, use them as documentation. To run:

`lein midje` will run all tests.

## TODO:

* debug macro extended to variadic to include into xrepl
* implement verbose output
* a way to execute batching requests from the command line

#### Copyright © 2014 *DMGT The Mailonline*
