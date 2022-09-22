package com.jpmarket.domain.verificationToken;

import com.jpmarket.config.utils.DoubleSubmitCheckToken;
import com.jpmarket.domain.user.User;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
public class VerificationToken {

    private static final ThreadLocal<String> EXPECTED_TOKEN = new ThreadLocal<>();

    private static final ThreadLocal<String> ACTUAL_TOKEN = new ThreadLocal<>();

    @Value("${jpmarget.app.jwtExpirationMs}")
    private int EXPIRATION;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private Date expiryDate;

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    @Builder
    public VerificationToken(String token, User user){
        this.token = token;
        this.user = user;
        this.expiryDate = this.calculateExpiryDate(5);
    }

    @PrePersist
    public void checkDoubleSubmit(){
        val expected = EXPECTED_TOKEN.get();
        val actual = ACTUAL_TOKEN.get();
        System.out.println("checkDoubleSubmit " + expected + " " + actual);
        if (expected != null && actual != null && Objects.equals(expected, actual)) {
            throw new IllegalArgumentException("double submit"); // ★一致しない場合は、例外をスローする
        }

    }
}
