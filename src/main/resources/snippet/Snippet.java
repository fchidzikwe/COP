package snippet;

public class Snippet {
	public static void main(String[] args) {
		http.authorizeRequests().antMatchers("/webjars/**").permitAll();
	}
}

