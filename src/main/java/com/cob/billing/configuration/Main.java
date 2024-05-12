package com.cob.billing.configuration;


import com.cob.billing.model.integration.claimmd.Charge;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.nimbusds.jose.shaded.json.JSONObject;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    static File ddd = new File("C:\\cob\\documents\\billing\\form-cms1500.pdf");

    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        String response = "500 Internal Server Error: {\"errorMessage\":\"Failed to send execute actions email\"}";
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
//        sendMail();
        //CreateRedirectFile.create();
//        List<String> firstList = List.of("AU", "90");
//        List<String> secondList = List.of("GP", "90","33");
//        List<String> result =ListShiftUtil.leftShift(firstList, secondList);
//        System.out.println(result);
        //reflectionTest();
//        nestedList();
        //groupList();


//        PdfReader reader = new PdfReader(ddd);
//        PdfDocument existingPdf = new PdfDocument(reader, new PdfWriter("filled-form.pdf"));
//
//        PdfAcroForm cmsForm = PdfAcroForm.getAcroForm(existingPdf, true);
//        existingPdf.removePage(2);
//        cmsForm.flattenFields();
//        for (Map.Entry<String, PdfFormField> entry : cmsForm.getAllFormFields().entrySet()) {
//            entry.getValue().getFontSize();
//            System.out.println(entry.getKey() + " " + entry.getValue().getDisplayValue());
//        }
//        existingPdf.close();
//        reader.close();

//        PdfDocument pdf = new PdfDocument(new PdfWriter("final.pdf"));
//        PdfMerger merger = new PdfMerger(pdf);
//
//        PdfReader source = new PdfReader("filled-form.pdf");
//        PdfDocument sourceDoc = new PdfDocument(source);
//        merger.merge(sourceDoc, 1, sourceDoc.getNumberOfPages());
//
//        PdfReader dest = new PdfReader("tmp.pdf");
//        PdfDocument destDoc = new PdfDocument(dest);
//        merger.merge(destDoc, 1, destDoc.getNumberOfPages());
//        sourceDoc.close();
//        destDoc.close();
//        File dd = new File("filled-form.pdf");
//        dd.delete();
//        File ss = new File("tmp.pdf");
//        ss.delete();
//        pdf.close();
    }

    private static void sendMail() {
        final String username = "mohammed.adel.abo.foutouh@gmail.com";
        final String password = "ffdd eovw ixzr awno";
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("mohammed.adel.abo.foutouh@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("mohammed.adel.abo.foutouh@gmail.com")
            );
            message.setSubject("Testing Gmail TLS");
            message.setText("Dear Mail Crawler,"
                    + "\n\n Please do not spam my email!");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void createNewDoc() throws IOException {
        //doc.copyPagesTo(1,1,existingPdf,2);
        PdfReader reader = new PdfReader(ddd);
        PdfWriter writer = new PdfWriter("tmp.pdf");
        PdfDocument document = new PdfDocument(reader, writer);
        PdfAcroForm cmsForm = PdfAcroForm.getAcroForm(document, true);
        cmsForm.getField("pt_name").setValue("khaled");
        document.removePage(2);
        cmsForm.flattenFields();
        document.close();

    }

    private static void groupList() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
//        list.add(5);
//        list.add(6);
//        list.add(7);
//        list.add(8);
//        list.add(9);
//        list.add(10);
//        list.add(11);
//        list.add(12);
//        list.add(13);
//        list.add(14);
//        list.add(15);
//        list.add(16);
//        list.add(17);
//        list.add(18);
//        list.add(19);
//        list.add(20);

        int chunkSize = 6;
        List<List<Integer>> groupedLists = IntStream.range(0, (list.size() + chunkSize - 1) / chunkSize)
                .mapToObj(i -> list.subList(i * chunkSize, Math.min((i + 1) * chunkSize, list.size())))
                .collect(Collectors.toList());
    }

    //    private static void nestedList() {
//        List<List<String>> list = new ArrayList<>();
//        List<String> list1 = new ArrayList<>();
//        list1.add("list1-objA");
//        list1.add("list1-objB");
//        list1.add("list1-objC");
//
//        List<String> list2 = new ArrayList<>();
//        list2.add("list2-objAA");
//        list2.add("list2-objBB");
//        list2.add("list2-objCC");
//        list.add(list1);
//        list.add(list2);
//
//        list.stream()
//                .flatMap(List::stream) // Flatten the nested lists into a single stream
//                .forEach(element -> {
//                    // Your logic for each element here
//                    System.out.println(element);
//                });
//
//    }
    private static void reflectionTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Charge charge = new Charge();
        //charge.setMod1();
//        Method method = Charge.class.getMethod("setMod"+1,String.class);
//        method.invoke(charge,"GP11");
        System.out.println(charge.getMod1());
    }
}
