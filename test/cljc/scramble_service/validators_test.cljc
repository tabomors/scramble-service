(ns scramble-service.validators-test
  (:require [clojure.test :refer :all]
            [scramble-service.validators :as validators :refer [validate]]))

(def only-chars-error {:error "Only chars are allowed!"})

(deftest validate-correct-values
  (testing "Should return true if value passed the validation"
    (is (= true (validate "abc" validators/only-chars? only-chars-error)))
    (is (= true (validate "aAdasdQWevzxZASsakdAobc" validators/only-chars? only-chars-error))))
  
  (testing "Should return false if value didn't pass the validation"
    (is (= only-chars-error (validate "abc123" validators/only-chars? only-chars-error)))
    (is (= only-chars-error (validate "aAdasdQ;;WevzxZASsakdAobc" validators/only-chars? only-chars-error)))))