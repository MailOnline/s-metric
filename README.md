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

        java -jar target/s-metric.jar this that

* It should print: Combined Score: 20.0%

## As a library

    :dependencies [
      [s-metric "0.0.3"] ;; copy this into your project.clj
     ]

WIP docs

## Metrics

The *Levenshtein Distance* is one of the most popular distance metrics. It offers the possiblity to calculate the best match of a set of transformations and is the most powerful.

The *Hamming distance* algorithm presented here is an adaptation of the original definition which applies to string of the same length. If strings are not of the same length, the shorter one is padded to the left with spaces and then the comparison begins. This approach has quite a bit of trafde-offs (it gives zero scores to strings that appears very similar if the padding is done in the wrong direction and returns bogus results if any of the strings contain spaces to begin with). Unless you control padding of the strings, use with strings of the same length only.

The *Naive score* approach splits both strings into increasingly bigger slices, starting from pairs of contigous chars. It collects score for each matching slice, exploding in a O(n-1)^2 complexity in the worst scenario (n is the length of both strings). Use naive scoring when you are interested in comparing substrings of the main two strings more than editing distance.

The *Combined metric* allows for free combination of the available metrics, combining the results. The default one from the command line combines them all. If you use s-metric as a library you can mix and match them plus adding your own.

## How to run the tests

Tests are covering all useful scenarios, use them as documentation. To run:

`lein midje` will run all tests.

## TODO:

* debug macro extended to variadic to include into xrepl
* implement verbose output
* a way to execute batching requests from the command line

#### Copyright © 2014 DMGT The Mailonline
