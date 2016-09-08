package com.foureverinbeta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Ryan on 9/8/16.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonCaught {
    private Integer id;
    private String payload;
}
