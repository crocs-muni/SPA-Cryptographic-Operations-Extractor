/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.sbapr.dataprocessing;

/**
 * In future these data will be passed to resource file.
 * 
 * @author Martin
 */
public class ExtractionHelp {
    public static String codeForInstruction(String instruction) {
        String code = "";
        switch (instruction) {
            case "B0":
                code = "for (short i = 0; i < pauseCycles; i++) {}\n" +
                        "m_secureRandom.generateData(m_RAMData, (short) 0, (short) 128);\n" +
                        "for (short i = 0; i < pauseCycles; i++) {}\n" +
                        "m_secureRandom.generateData(m_RAMData, (short) 0, (short) 128);\n" +
                        "m_secureRandom.generateData(m_RAMData, (short) 0, (short) 128);\n" +
                        "for (short i = 0; i < pauseCycles; i++) {}\n" +
                        "m_secureRandom.generateData(m_RAMData, (short) 0, (short) 128);\n" +
                        "m_secureRandom.generateData(m_RAMData, (short) 0, (short) 128);\n" +
                        "m_secureRandom.generateData(m_RAMData, (short) 0, (short) 128);\n" +
                        "for (short i = 0; i < pauseCycles; i++) {}\n";
                return code;
            case "B1":
                code = "beginDivision();\n" +
                        "m_aesKey.setKey(m_RAMKey, (short) 0);\n" +
                        "middleDivision();\n" +
                        "m_aesKey.setKey(m_RAMKey, (short) 0);\n" +
                        "endDivision();";
                return code;
            case "B2":
                code = "beginDivision();\n" +
                        "m_desKey.setKey(m_RAMKey, (short) 0);\n" +
                        "middleDivision();\n" +
                        "m_desKey.setKey(m_RAMKey, (short) 0);\n" +
                        "endDivision();";
                return code;
            case "B3":
                code = "beginDivision();\n" +
                        "m_aesCipher.doFinal(m_RAMData, (short) 0, (short) 16, m_RAMData, (short) 16);\n" +
                        "middleDivision();\n" +
                        "m_aesCipher.doFinal(m_RAMData, (short) 0, (short) 16, m_RAMData, (short) 16);\n" +
                        "endDivision();";
                return code;
            case "B4":
                code = "beginDivision();\n" +
                        "m_desCipher.doFinal(m_RAMData, (short) 0, (short) 16, m_RAMData, (short) 16);\n" +
                        "middleDivision();\n" +
                        "m_desCipher.doFinal(m_RAMData, (short) 0, (short) 16, m_RAMData, (short) 16);\n" +
                        "endDivision();";
                return code;
            case "B5":
                code = "beginDivision();\n" +
                        "m_SHA1Hash.doFinal(m_RAMData, (short) 0, (short) 16, m_RAMData, (short) 16);\n" +
                        "middleDivision();\n" +
                        "m_SHA1Hash.doFinal(m_RAMData, (short) 0, (short) 16, m_RAMData, (short) 16);\n" +
                        "endDivision();";
                return code;
            case "B6":
                code = "beginDivision();\n" +
                        "m_SHA256Hash.doFinal(m_RAMData, (short) 0, (short) 16, m_RAMData, (short) 0);\n" +
                        "middleDivision();\n" +
                        "m_SHA256Hash.doFinal(m_RAMData, (short) 0, (short) 16, m_RAMData, (short) 0);\n" +
                        "endDivision();";
                return code;
            case "B7":
                code = "for (short i = 0; i < 5000; i++) {}\n" +
                        "m_RSAKeyPair.genKeyPair();\n" +
                        "for (short i = 0; i < 5000; i++) {}";
                return code;
            case "B8":
                code = "beginDivision();\n" +
                        "m_RSASign.sign(m_RAMData, (short) 0, (short) 16, m_RAMData, (short) 16);\n" +
                        "middleDivision();\n" +
                        "m_RSASign.sign(m_RAMData, (short) 0, (short) 16, m_RAMData, (short) 16);\n" +
                        "endDivision();";
                return code;
            case "B9":
                code = "for (short i = 0; i < 5000; i++) {}\n" +
                        "m_EC192FPKeyPair.genKeyPair();\n" +
                        "for (short i = 0; i < 5000; i++) {} ";
                return code;
            case "BA":
                code = "beginDivision();\n" +
                        "m_EC192FPSign.sign(m_RAMData, (short) 0, (short) 16, m_RAMData, (byte) 16);\n" +
                        "middleDivision();\n" +
                        "m_EC192FPSign.sign(m_RAMData, (short) 0, (short) 16, m_RAMData, (byte) 16);\n" +
                        "endDivision();";
                return code;
            case "BB":
                code = "for (short i = 0; i < 5000; i++) {}\n" +
                        "m_EC256FPKeyPair.genKeyPair();\n" +
                        "for (short i = 0; i < 5000; i++) {} ";
                return code;
            case "BC":
                code = "beginDivision();\n" +
                        "m_EC256FPSign.sign(m_RAMData, (short) 0, (short) 16, m_RAMData, (byte) 0);\n" +
                        "middleDivision();\n" +
                        "m_EC256FPSign.sign(m_RAMData, (short) 0, (short) 16, m_RAMData, (byte) 0);\n" +
                        "endDivision();";
                return code;
            default: return code;
        }
    }
}
