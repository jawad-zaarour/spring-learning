package zaarour.dev.cashcard;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;


@Configuration
@EnableMethodSecurity
public class CashCardSecurityConfig {

    /*
    * If the request is GET /cashcards, require read access;
    * else if it's any other /cashcards request, require write access;
    * at least require authentication for any other request.
    *
    * */
    @Bean
    SecurityFilterChain appSecurity(HttpSecurity http, CachCardAuthenticationEntryPoint entryPoint) throws Exception {
        http.authorizeHttpRequests(authorize ->
                        authorize
                                //JWT must be granted the cashcard:read scope in order to access GET URIs that begin with /cashcards.
                                .requestMatchers(HttpMethod.GET, "/cashcards/**").hasAuthority("SCOPE_cashcard:read")

                                //JWT must be granted the cashcard:write scope for URIs that begin with /cashcards, for non-GET /cashcards endpoints.
                                .requestMatchers("/cashcards/**").hasAuthority("SCOPE_cashcard:write")

                                // Any request that does not match specific patterns defined above will require to be authenticated.
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer((oauth2) -> oauth2
                        .authenticationEntryPoint(entryPoint)
                        .jwt(Customizer.withDefaults())
                );
        return http.build();
    }
}
