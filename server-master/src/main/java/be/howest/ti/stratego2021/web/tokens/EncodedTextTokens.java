package be.howest.ti.stratego2021.web.tokens;

import be.howest.ti.stratego2021.web.exceptions.InvalidTokenException;


public class EncodedTextTokens implements TokenManager {

    @Override
    public String createToken(String gameId, String player) {
        return encodeFilteredRoomId(gameId + "-" + player);
    }

    @Override
    public String token2gameId(String token) {
        return parseToken(token, 0);
    }

    @Override
    public String token2player(String token) {
        return parseToken(token, 1);
    }

    private String parseToken(String token, int part) {
        String decodedToken = decodeEncryptedToken(token);

        String[] parts = decodedToken.split("-");
        if (parts.length != 2) {
            throw new InvalidTokenException();
        }
        return parts[part];
    }

    public String encodeFilteredRoomId(String roomId) {
        return encodeRoomId(removeSpecialCharactersFromId(roomId));
    }

    private String removeSpecialCharactersFromId(String roomId) {
        return roomId.replaceAll("[^a-zA-Z0-9-]", "");
    }

    private String encodeRoomId(String filteredRoomId) {
        char[] filteredIdArray = filteredRoomId.toCharArray();
        int[] encodedRoomId = new int[filteredRoomId.length()];

        for (int i = 0; i < filteredRoomId.length(); i++) {
            encodedRoomId[i] = (int) filteredIdArray[i] + i;
        }
        return transferEncryptedTokenToNewString(encodedRoomId);
    }

    private String transferEncryptedTokenToNewString(int[] encodedRoomId) {
        char[] encryptedStringToken =  new char[encodedRoomId.length];

        for (int i = 0; i < encodedRoomId.length; i++) {
            encryptedStringToken[i] = (char) (encodedRoomId[i]);
        }
        return new String(encryptedStringToken);
    }

    public String getDecryptedToken(String encryptedStringToken) {
        return decodeEncryptedToken(encryptedStringToken);
    }

    private String decodeEncryptedToken(String encryptedStringToken) {
        char[] encryptedStringTokenArray = encryptedStringToken.toCharArray();

        for (int i = 0; i < encryptedStringToken.length(); i++) {
            encryptedStringTokenArray[i] = (char) (encryptedStringTokenArray[i] + (i * -1));
        }
        return new String(encryptedStringTokenArray);
    }

}

