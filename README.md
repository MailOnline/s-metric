# s-metric

s-metric is a set of metrics to score how close a string compare to another. It applies to all problems related to text similarity, including suggestions for mispelled words and many others. The current metrics are just two (Hamming distance with padding and naive brute force) and a combination of them, but others are under development and will be added soon. It can be used as a library or as a command line tool.

## Command line tool

* Useful to use in shell scripting or quick string comparisons
* To create the uberjar: please make sure you have leiningen installed, then lein uberjar at the project root after git cloning the project.
* It will print the following options:

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
      [s-metric "0.0.2"] ;; copy this into your project.clj
     ]

And then:

    (ns your-prj-here.core
      (:require
       [s-metric.combined :as distance] ;; and this in your ns
      ))

## Metrics

The *Hamming distance* algorithm presented here is an adaptation of the original definition which applies to string of the same length. If strings are not of the same length, the shorter one is padded to the left with spaces and then the comparison begins. This approach has quite a bit of trafde-offs (it gives zero scores to strings that appears very similar if the padding is done in the wrong direction and returns bogus results if any of the strings contain spaces to begin with).

The *Naive score* approach splits both strings into increasingly bigger slices, starting from pairs of contigous chars. It collects score for each matching slice, exploding in a O(n-1)^2 complexity in the worst scenario (n is the length of both strings).

The *Combined metric* is just the application of all other other metrics to the same string comparison. The combined metric also contains bulk-matching functions that can be applied to a collection of targets at once returning all the found scores.

## How to run the tests

Tests are covering all useful scenarios, use them as documentation. To run:

`lein midje` will run all tests.

## TODO:

* debug macro extended to variadic to include into xrepl
* protocol for metrics containing max-score, match so you can extend s-metric with your own metrics outside the project
* implement verbose output
* rename hamming to special-hamming with padding left or make it configurable

#### Copyright © 2014 DMGT The Mailonline
