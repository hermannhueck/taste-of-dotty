package dotty.samples._01types

object TypeLambdas:

    // variance annotations not allowed since 0.22.RC1
    type AMap = [X, Y] =>> Map[Y, X]

    type T1[X] = (X, X)

    // is a shorthand for:

    type T2 = [X] =>> (X, X)