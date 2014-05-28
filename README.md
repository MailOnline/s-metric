# s-metric

s-metric is a set of metrics to score how close a string compare to another. The current metrics are just two (Hamming distance with padding and naive brute force) but others are under development and will be added soon. It comes as a library and as a command line tool.

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
      [s-metric "0.0.1"] ;; copy this into your project.clj
     ]

And then:

    (ns your-prj-here.core
      (:require
       [s-metric.combined :as distance] ;; and this in your ns
      ))


## How to run the tests

Tests are covering all useful scenarios, use them as documentation. To run:

`lein midje` will run all tests.

## TODO:

* debug macro extended to variadic to include into xrepl
* protocol for metrics containing max-score, match so you can extend s-metric with your own metrics outside the project
* implement verbose output
* rename hamming to special-hamming with padding left

#### Copyright © 2014 DMGT The Mailonline
