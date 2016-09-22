package a;

public class PatternDecorator {

	public PatternDecorator() {
		Ball ball = new BigBall(new BigBall(new RedBall(new SimpleBall())));
		System.out.println(ball.getDescription());
	}

	abstract class Ball {
		abstract public String getDescription();
	}

	class SimpleBall extends Ball {
		public String getDescription() {
			return "simple ball";
		}
	}

	class RedBall extends Ball {
		public RedBall(Ball ball) {
			this.ball = ball;
		}

		@Override
		public String getDescription() {
			return "red " + ball.getDescription();
		}

		private Ball ball;
	}

	class BigBall extends Ball {
		public BigBall(Ball ball) {
			this.ball = ball;
		}

		@Override
		public String getDescription() {
			return "big " + ball.getDescription();
		}

		private Ball ball;
	}
}
