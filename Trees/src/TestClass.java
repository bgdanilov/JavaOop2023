class TestClass{
    public static void main (String[] args)   {
        String str1 = "Java";
        String str2 = "Java";
        String str3 = "ASP";
        int val = 0;
        val = str1.compareTo(str2);
        System.out.println(val);
        val = str1.compareTo(str3);
        System.out.println(val);
        val = str3.compareTo(str1);
        System.out.println(val);
    }
}