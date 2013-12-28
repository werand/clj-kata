(ns string-calculator.core-test
  (:use midje.sweet
        string-calculator.core))

(facts "Facts about add"
       (fact "it returns 0 for empty input"
         (add "") => 0)
       (fact "it returns 0 for empty input"
         (add "1") => 1)
       (fact "adds sequences of two values"
         (add "1, 2") => 3)
       (fact "adds sequences of any numbers"
         (add "1, 2, 3") => 6)
       (fact "newline is a valid separator"
         (add "1\n2, 3") => 6)
       (fact "you are able to specify a separator"
         (add "//;\n1;2") => 3)
       (fact "negative values are not allowed"
         (add "1, -2") => (throws Exception "Negative Zahlen sind nicht erlaubt. Fehlerhafte Zahl: -2"))
       (fact "negative values are not allowed"
         (add "1, -2, -3") => (throws Exception "Negative Zahlen sind nicht erlaubt. Fehlerhafte Zahlen: -2, -3"))
       (fact "values bigger than 1000 are ignored"
         (add "1, 1000, 1001") => 1001)
       (fact "separator may be very long"
         (add "//[***]\n1***2") => 3)
       (fact "several separators may be specified"
         (add "//[one][;][__]\n1 one 1 ; 1 one 1 __ 1") => 5
         (add "//[*][%]\n1*,2%3") => 6)
         (add "//[***][%]\n1***,2%3") => 6)
