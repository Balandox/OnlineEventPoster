package org.suai.courceWork.exceptions;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotEnoughElementsException extends ArithmeticException {

    private String name;
    private int amount;

    public NotEnoughElementsException(String s, String name, int amount) {
        super(s);
        this.name = name;
        this.amount = amount;
    }

}