package com.example.divisist.divisist.divisiondesistemas;


public class CardItemData
{
	private String Materia;
	private String PP;
	private String SP;
    private String TP;
    private String EX;
    private String HA;
    private String Def;

	public CardItemData(String text1, String text2, String text3, String text4, String text5, String text6, String text7)
	{
		Materia = text1;
		PP = text2;
	    SP = text3;
        TP = text4;
        EX = text5;
        HA = text6;
        Def = text7;
	}

	public String getText1()
	{
		return Materia;
	}


    public String getText2()
    {
        return PP;
    }

    public String getText3()
    {
        return SP;
    }

    public String getText4()
    {
        return TP;
    }


    public String getText5()
    {
        return EX;
    }

    public String getText6()
    {
        return HA;
    }

    public String getText7()
    {
        return Def;
    }
}
