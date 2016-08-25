package com.loy.e.security.pwd.service;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.crypto.hash.format.ModularCryptFormat;
import org.apache.shiro.crypto.hash.format.ParsableHashFormat;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.StringUtils;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
public class LoyHashFormat implements ModularCryptFormat, ParsableHashFormat {
    public static final String ID = "loy";
    public static final String MCF_PREFIX = TOKEN_DELIMITER + ID + TOKEN_DELIMITER;

    @Override
    public String format(Hash hash) {
        if (hash == null) {
            return null;
        }

        String algorithmName = hash.getAlgorithmName();
        ByteSource salt = hash.getSalt();
        int iterations = hash.getIterations();
        StringBuilder sb = new StringBuilder(MCF_PREFIX)
                .append(algorithmName)
                .append(TOKEN_DELIMITER)
                .append(iterations)
                .append(TOKEN_DELIMITER);

        if (salt != null) {
            sb.append(salt.toBase64());
        }
        sb.append(TOKEN_DELIMITER);
        sb.append(hash.toBase64());
        return sb.toString();
    }

    @Override
    public Hash parse(String formatted) {
        if (formatted == null) {
            return null;
        }
        if (!formatted.startsWith(MCF_PREFIX)) {
            String msg = "The argument is not a valid '" + ID + "' formatted hash.";
            throw new IllegalArgumentException(msg);
        }

        String suffix = formatted.substring(MCF_PREFIX.length());
        String[] parts = suffix.split("\\$");

        //last part is always the digest/checksum, Base64-encoded:
        int i = parts.length - 1;
        String digestBase64 = parts[i--];
        //second-to-last part is always the salt, Base64-encoded:
        String saltBase64 = parts[i--];
        String iterationsString = parts[i--];
        String algorithmName = parts[i];

        byte[] digest = Base64.decode(digestBase64);
        ByteSource salt = null;

        if (StringUtils.hasLength(saltBase64)) {
            byte[] saltBytes = Base64.decode(saltBase64);
            salt = ByteSource.Util.bytes(saltBytes);
        }

        int iterations;
        try {
            iterations = Integer.parseInt(iterationsString);
        } catch (NumberFormatException e) {
            String msg = "Unable to parse formatted hash string: " + formatted;
            throw new IllegalArgumentException(msg, e);
        }

        SimpleHash hash = new SimpleHash(algorithmName);
        hash.setBytes(digest);
        if (salt != null) {
            hash.setSalt(salt);
        }
        hash.setIterations(iterations);

        return hash;
    }

    @Override
    public String getId() {
        return ID;
    }

}