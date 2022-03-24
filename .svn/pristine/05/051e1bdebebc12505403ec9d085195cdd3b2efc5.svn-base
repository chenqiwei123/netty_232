package com.example.netty.netty.Socket.Parsing;

import java.nio.charset.StandardCharsets;

import static ch.qos.logback.core.encoder.ByteArrayUtil.hexStringToByteArray;

public class CRC16 {
    private static byte[] _auchCRCHi = hexStringToByteArray("00C1814001C0804101C0804100C1814001C0804100C1814000C1814001C0804101C0804100C1814000C1814001C0804100C1814001C0804101C0804100C1814001C0804100C1814000C1814001C0804100C1814001C0804101C0804100C1814000C1814001C0804101C0804100C1814001C0804100C1814000C1814001C0804101C0804100C1814000C1814001C0804100C1814001C0804101C0804100C1814000C1814001C0804101C0804100C1814001C0804100C1814000C1814001C0804100C1814001C0804101C0804100C1814001C0804100C1814000C1814001C0804101C0804100C1814000C1814001C0804100C1814001C0804101C0804100C18140");

    public static byte[] _auchCRCLo =
                    hexStringToByteArray("00C0C101C30302C2C60607C705C5C404CC0C0DCD0FCFCE0E0ACACB0BC90908C8D81819D91BDBDA1A1EDEDF1FDD1D1CDC14D4D515D71716D6D21213D311D1D010F03031F133F3F23236F6F737F53534F43CFCFD3DFF3F3EFEFA3A3BFB39F9F83828E8E929EB2B2AEAEE2E2FEF2DEDEC2CE42425E527E7E62622E2E323E12120E0A06061A163A3A26266A6A767A56564A46CACAD6DAF6F6EAEAA6A6BAB69A9A86878B8B979BB7B7ABABE7E7FBF7DBDBC7CB47475B577B7B67672B2B373B17170B0509091519353529296565797559594549C5C5D9D5F9F9E5E5A9A9B5B99595898884849894B8B8A4A4E8E8F4F8D4D4C8C44848545874746868242438341818040");


    public static Integer CalculateCrc16(byte[] buffer)
    {
        for (int i=0;i<buffer.length;i++){
            System.out.printf(String.valueOf(buffer[i]));
        }
        System.out.println("");
        int crcHi =  255;  // 高位初始化

        int crcLo =  255;  // 低位初始化

        for (int i = 0; i < buffer.length; i++)
        {
            int crcIndex = crcHi ^ buffer[i]; //查找crc表值
            if (crcIndex<0){
                crcIndex=crcIndex+256;
            }
            crcHi =(crcLo ^ _auchCRCHi[crcIndex]);
            if (crcHi<0){
                crcHi= crcHi+256;
            }
            crcLo = _auchCRCLo[crcIndex];
            if (crcLo<0){
                crcLo=crcLo+256;
            }
            //System.out.printf(crcIndex+""+crcHi+""+crcLo);
        }

        return (Integer) (crcHi << 8 | crcLo);
    }

    public static void main(String[] args) {
        String s_Result = String.format("%.3f", Double.parseDouble("0"));
        System.out.println(s_Result);
        System.out.println(CRC16.class.getName());
        CRC16 crc16=new CRC16();
        Class s=crc16.getClass();
        System.out.printf(s.getName());
        System.out.printf("CalculateCrc16:"+Integer.toHexString(CalculateCrc16("ST=91;CN=2051;CP=&&QN=20211001153010835&&".getBytes(StandardCharsets.UTF_8))));
    }
}
