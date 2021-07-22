package pl.pilichm.quizapp

data class UserScore(var scoreNumber: Int, val userName: String, val score: Int) {
    companion object {
        fun getMockUserScore() = UserScore(0, "__________", 0)
    }
}