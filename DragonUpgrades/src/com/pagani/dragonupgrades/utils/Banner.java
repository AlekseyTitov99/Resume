package com.pagani.dragonupgrades.utils;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.HashMap;

public class Banner {

    private static final HashMap<Character, ItemStack> redBanners = new HashMap<>();

    static {
        buildBanners(DyeColor.ORANGE, DyeColor.BLACK, true);
    }

    private static void buildBanners(DyeColor cima, DyeColor base, Boolean color) {
        ItemStack bannerA = new ItemStack(Material.BANNER);
        BannerMeta bannerMetaA = (BannerMeta) bannerA.getItemMeta();
        bannerMetaA.setBaseColor(cima);
        bannerMetaA.addPattern(new Pattern(base, PatternType.STRIPE_TOP));
        bannerMetaA.addPattern(new Pattern(base, PatternType.STRIPE_RIGHT));
        bannerMetaA.addPattern(new Pattern(base, PatternType.STRIPE_LEFT));
        bannerMetaA.addPattern(new Pattern(base, PatternType.STRIPE_MIDDLE));
        bannerMetaA.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMetaA.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        bannerA.setItemMeta(bannerMetaA);

        ItemStack bannerB = new ItemStack(Material.BANNER);
        BannerMeta bannerMetaB = (BannerMeta) bannerB.getItemMeta();
        bannerMetaB.setBaseColor(cima);
        bannerMetaB.addPattern(new Pattern(base, PatternType.STRIPE_LEFT));
        bannerMetaB.addPattern(new Pattern(base, PatternType.STRIPE_BOTTOM));
        bannerMetaB.addPattern(new Pattern(base, PatternType.STRIPE_RIGHT));
        bannerMetaB.addPattern(new Pattern(base, PatternType.STRIPE_TOP));
        bannerMetaB.addPattern(new Pattern(base, PatternType.STRIPE_MIDDLE));
        bannerMetaB.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMetaB.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        bannerB.setItemMeta(bannerMetaB);

        ItemStack bannerC = new ItemStack(Material.BANNER);
        BannerMeta bannerMetaC = (BannerMeta) bannerC.getItemMeta();
        bannerMetaC.setBaseColor(cima);
        bannerMetaC.addPattern(new Pattern(base, PatternType.STRIPE_LEFT));
        bannerMetaC.addPattern(new Pattern(base, PatternType.STRIPE_TOP));
        bannerMetaC.addPattern(new Pattern(base, PatternType.STRIPE_BOTTOM));
        bannerMetaC.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMetaC.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        bannerC.setItemMeta(bannerMetaC);

        ItemStack bannerD = new ItemStack(Material.BANNER);
        BannerMeta bannerMetaD = (BannerMeta) bannerD.getItemMeta();
        bannerMetaD.setBaseColor(cima);
        bannerMetaD.addPattern(new Pattern(base, PatternType.STRIPE_RIGHT));
        bannerMetaD.addPattern(new Pattern(base, PatternType.STRIPE_BOTTOM));
        bannerMetaD.addPattern(new Pattern(base, PatternType.STRIPE_TOP));
        bannerMetaD.addPattern(new Pattern(cima, PatternType.CURLY_BORDER));
        bannerMetaD.addPattern(new Pattern(base, PatternType.STRIPE_LEFT));
        bannerMetaD.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMetaD.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        bannerD.setItemMeta(bannerMetaD);

        ItemStack bannerE = new ItemStack(Material.BANNER);
        BannerMeta bannerMetaE = (BannerMeta) bannerE.getItemMeta();
        bannerMetaE.setBaseColor(cima);
        bannerMetaE.addPattern(new Pattern(base, PatternType.STRIPE_MIDDLE));
        bannerMetaE.addPattern(new Pattern(cima, PatternType.STRIPE_RIGHT));
        bannerMetaE.addPattern(new Pattern(base, PatternType.STRIPE_LEFT));
        bannerMetaE.addPattern(new Pattern(base, PatternType.STRIPE_TOP));
        bannerMetaE.addPattern(new Pattern(base, PatternType.STRIPE_BOTTOM));
        bannerMetaE.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMetaE.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        bannerE.setItemMeta(bannerMetaE);

        ItemStack bannerF = new ItemStack(Material.BANNER);
        BannerMeta bannerMetaF = (BannerMeta) bannerF.getItemMeta();
        bannerMetaF.setBaseColor(cima);
        bannerMetaF.addPattern(new Pattern(base, PatternType.STRIPE_MIDDLE));
        bannerMetaF.addPattern(new Pattern(cima, PatternType.STRIPE_RIGHT));
        bannerMetaF.addPattern(new Pattern(base, PatternType.STRIPE_LEFT));
        bannerMetaF.addPattern(new Pattern(base, PatternType.STRIPE_TOP));
        bannerMetaF.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMetaF.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        bannerF.setItemMeta(bannerMetaF);

        ItemStack bannerG = new ItemStack(Material.BANNER);
        BannerMeta bannerMetaG = (BannerMeta) bannerG.getItemMeta();
        bannerMetaG.setBaseColor(cima);
        bannerMetaG.addPattern(new Pattern(base, PatternType.STRIPE_RIGHT));
        bannerMetaG.addPattern(new Pattern(cima, PatternType.HALF_HORIZONTAL));
        bannerMetaG.addPattern(new Pattern(base, PatternType.STRIPE_BOTTOM));
        bannerMetaG.addPattern(new Pattern(base, PatternType.STRIPE_LEFT));
        bannerMetaG.addPattern(new Pattern(base, PatternType.STRIPE_TOP));
        bannerMetaG.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMetaG.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        bannerG.setItemMeta(bannerMetaG);

        ItemStack bannerH = new ItemStack(Material.BANNER);
        BannerMeta bannerMetaH = (BannerMeta) bannerH.getItemMeta();
        bannerMetaH.setBaseColor(cima);
        bannerMetaH.addPattern(new Pattern(base, PatternType.STRIPE_MIDDLE));
        bannerMetaH.addPattern(new Pattern(base, PatternType.STRIPE_RIGHT));
        bannerMetaH.addPattern(new Pattern(base, PatternType.STRIPE_LEFT));
        bannerMetaH.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMetaH.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        bannerH.setItemMeta(bannerMetaH);

        ItemStack bannerI = new ItemStack(Material.BANNER);
        BannerMeta bannerMetaI = (BannerMeta) bannerI.getItemMeta();
        bannerMetaI.setBaseColor(cima);
        bannerMetaI.addPattern(new Pattern(base, PatternType.STRIPE_TOP));
        bannerMetaI.addPattern(new Pattern(base, PatternType.STRIPE_BOTTOM));
        bannerMetaI.addPattern(new Pattern(base, PatternType.STRIPE_CENTER));
        bannerMetaI.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMetaI.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        bannerI.setItemMeta(bannerMetaI);

        ItemStack bannerJ = new ItemStack(Material.BANNER);
        BannerMeta bannerMetaJ = (BannerMeta) bannerJ.getItemMeta();
        bannerMetaJ.setBaseColor(cima);
        bannerMetaJ.addPattern(new Pattern(base, PatternType.STRIPE_LEFT));
        bannerMetaJ.addPattern(new Pattern(cima, PatternType.HALF_HORIZONTAL));
        bannerMetaJ.addPattern(new Pattern(base, PatternType.STRIPE_BOTTOM));
        bannerMetaJ.addPattern(new Pattern(base, PatternType.STRIPE_RIGHT));
        bannerMetaJ.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMetaJ.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        bannerJ.setItemMeta(bannerMetaJ);

        ItemStack bannerK = new ItemStack(Material.BANNER);
        BannerMeta bannerMetaK = (BannerMeta) bannerK.getItemMeta();
        bannerMetaK.setBaseColor(cima);
        bannerMetaK.addPattern(new Pattern(base, PatternType.STRIPE_LEFT));
        bannerMetaK.addPattern(new Pattern(base, PatternType.STRIPE_LEFT));
        bannerMetaK.addPattern(new Pattern(base, PatternType.STRIPE_MIDDLE));
        bannerMetaK.addPattern(new Pattern(cima, PatternType.HALF_VERTICAL_MIRROR));
        bannerMetaK.addPattern(new Pattern(base, PatternType.CROSS));
        bannerMetaK.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMetaK.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        bannerK.setItemMeta(bannerMetaK);

        ItemStack bannerL = new ItemStack(Material.BANNER);
        BannerMeta bannerMetaL = (BannerMeta) bannerL.getItemMeta();
        bannerMetaL.setBaseColor(cima);
        bannerMetaL.addPattern(new Pattern(base, PatternType.STRIPE_LEFT));
        bannerMetaL.addPattern(new Pattern(base, PatternType.STRIPE_BOTTOM));
        bannerMetaL.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMetaL.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        bannerL.setItemMeta(bannerMetaL);

        ItemStack bannerM = new ItemStack(Material.BANNER);
        BannerMeta bannerMetaM = (BannerMeta) bannerM.getItemMeta();
        bannerMetaM.setBaseColor(cima);
        bannerMetaM.addPattern(new Pattern(base, PatternType.TRIANGLE_TOP));
        bannerMetaM.addPattern(new Pattern(cima, PatternType.TRIANGLES_TOP));
        bannerMetaM.addPattern(new Pattern(base, PatternType.STRIPE_LEFT));
        bannerMetaM.addPattern(new Pattern(base, PatternType.STRIPE_RIGHT));
        bannerMetaM.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMetaM.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        bannerM.setItemMeta(bannerMetaM);

        ItemStack bannerN = new ItemStack(Material.BANNER);
        BannerMeta bannerMetaN = (BannerMeta) bannerN.getItemMeta();
        bannerMetaN.setBaseColor(cima);
        bannerMetaN.addPattern(new Pattern(base, PatternType.STRIPE_LEFT));
        bannerMetaN.addPattern(new Pattern(cima, PatternType.DIAGONAL_RIGHT_MIRROR));
        bannerMetaN.addPattern(new Pattern(base, PatternType.STRIPE_DOWNRIGHT));
        bannerMetaN.addPattern(new Pattern(base, PatternType.STRIPE_RIGHT));
        bannerMetaN.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMetaN.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        bannerN.setItemMeta(bannerMetaN);

        ItemStack bannerO = new ItemStack(Material.BANNER);
        BannerMeta bannerMetaO = (BannerMeta) bannerO.getItemMeta();
        bannerMetaO.setBaseColor(cima);
        bannerMetaO.addPattern(new Pattern(base, PatternType.STRIPE_LEFT));
        bannerMetaO.addPattern(new Pattern(base, PatternType.STRIPE_TOP));
        bannerMetaO.addPattern(new Pattern(base, PatternType.STRIPE_RIGHT));
        bannerMetaO.addPattern(new Pattern(base, PatternType.STRIPE_BOTTOM));
        bannerMetaO.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMetaO.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        bannerO.setItemMeta(bannerMetaO);

        ItemStack bannerP = new ItemStack(Material.BANNER);
        BannerMeta bannerMetaP = (BannerMeta) bannerP.getItemMeta();
        bannerMetaP.setBaseColor(base);
        bannerMetaP.addPattern(new Pattern(cima, PatternType.HALF_HORIZONTAL));
        bannerMetaP.addPattern(new Pattern(base, PatternType.STRIPE_RIGHT));
        bannerMetaP.addPattern(new Pattern(cima, PatternType.STRIPE_BOTTOM));
        bannerMetaP.addPattern(new Pattern(base, PatternType.STRIPE_LEFT));
        bannerMetaP.addPattern(new Pattern(base, PatternType.STRIPE_TOP));
        bannerMetaP.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMetaP.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        bannerP.setItemMeta(bannerMetaP);

        ItemStack bannerQ = new ItemStack(Material.BANNER);
        BannerMeta bannerMetaQ = (BannerMeta) bannerQ.getItemMeta();
        bannerMetaQ.setBaseColor(cima);
        bannerMetaQ.addPattern(new Pattern(base, PatternType.STRIPE_LEFT));
        bannerMetaQ.addPattern(new Pattern(base, PatternType.STRIPE_TOP));
        bannerMetaQ.addPattern(new Pattern(base, PatternType.STRIPE_RIGHT));
        bannerMetaQ.addPattern(new Pattern(base, PatternType.STRIPE_BOTTOM));
        bannerMetaQ.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMetaQ.addPattern(new Pattern(base, PatternType.SQUARE_BOTTOM_RIGHT));
        bannerMetaQ.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        bannerQ.setItemMeta(bannerMetaQ);

        ItemStack bannerR = new ItemStack(Material.BANNER);
        BannerMeta bannerMetaR = (BannerMeta) bannerR.getItemMeta();
        bannerMetaR.setBaseColor(cima);
        bannerMetaR.addPattern(new Pattern(base, PatternType.STRIPE_RIGHT));
        bannerMetaR.addPattern(new Pattern(base, PatternType.STRIPE_TOP));
        bannerMetaR.addPattern(new Pattern(cima, PatternType.HALF_HORIZONTAL_MIRROR));
        bannerMetaR.addPattern(new Pattern(base, PatternType.STRIPE_DOWNRIGHT));
        bannerMetaR.addPattern(new Pattern(base, PatternType.STRIPE_LEFT));
        bannerMetaR.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMetaR.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        bannerR.setItemMeta(bannerMetaR);

        ItemStack bannerS = new ItemStack(Material.BANNER);
        BannerMeta bannerMetaS = (BannerMeta) bannerS.getItemMeta();
        bannerMetaS.setBaseColor(cima);
        bannerMetaS.addPattern(new Pattern(base, PatternType.STRIPE_BOTTOM));
        bannerMetaS.addPattern(new Pattern(base, PatternType.STRIPE_TOP));
        bannerMetaS.addPattern(new Pattern(cima, PatternType.RHOMBUS_MIDDLE));
        bannerMetaS.addPattern(new Pattern(base, PatternType.STRIPE_DOWNRIGHT));
        bannerMetaS.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMetaS.addPattern(new Pattern(cima, PatternType.CURLY_BORDER));
        bannerMetaS.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        bannerS.setItemMeta(bannerMetaS);

        ItemStack bannerT = new ItemStack(Material.BANNER);
        BannerMeta bannerMetaT = (BannerMeta) bannerT.getItemMeta();
        bannerMetaT.setBaseColor(cima);
        bannerMetaT.addPattern(new Pattern(base, PatternType.STRIPE_TOP));
        bannerMetaT.addPattern(new Pattern(base, PatternType.STRIPE_CENTER));
        bannerMetaT.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMetaT.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        bannerT.setItemMeta(bannerMetaT);

        ItemStack bannerU = new ItemStack(Material.BANNER);
        BannerMeta bannerMetaU = (BannerMeta) bannerU.getItemMeta();
        bannerMetaU.setBaseColor(cima);
        bannerMetaU.addPattern(new Pattern(base, PatternType.STRIPE_LEFT));
        bannerMetaU.addPattern(new Pattern(base, PatternType.STRIPE_BOTTOM));
        bannerMetaU.addPattern(new Pattern(base, PatternType.STRIPE_RIGHT));
        bannerMetaU.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMetaU.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        bannerU.setItemMeta(bannerMetaU);

        ItemStack bannerV = new ItemStack(Material.BANNER);
        BannerMeta bannerMetaV = (BannerMeta) bannerV.getItemMeta();
        bannerMetaV.setBaseColor(cima);
        bannerMetaV.addPattern(new Pattern(base, PatternType.STRIPE_LEFT));
        bannerMetaV.addPattern(new Pattern(cima, PatternType.TRIANGLES_BOTTOM));
        bannerMetaV.addPattern(new Pattern(base, PatternType.STRIPE_DOWNLEFT));
        bannerMetaV.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMetaV.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        bannerV.setItemMeta(bannerMetaV);

        ItemStack bannerW = new ItemStack(Material.BANNER);
        BannerMeta bannerMetaW = (BannerMeta) bannerW.getItemMeta();
        bannerMetaW.setBaseColor(cima);
        bannerMetaW.addPattern(new Pattern(base, PatternType.TRIANGLE_BOTTOM));
        bannerMetaW.addPattern(new Pattern(cima, PatternType.TRIANGLES_BOTTOM));
        bannerMetaW.addPattern(new Pattern(base, PatternType.STRIPE_LEFT));
        bannerMetaW.addPattern(new Pattern(base, PatternType.STRIPE_RIGHT));
        bannerMetaW.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMetaW.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        bannerW.setItemMeta(bannerMetaW);

        ItemStack bannerX = new ItemStack(Material.BANNER);
        BannerMeta bannerMetaX = (BannerMeta) bannerX.getItemMeta();
        bannerMetaX.setBaseColor(cima);
        bannerMetaX.addPattern(new Pattern(base, PatternType.STRIPE_TOP));
        bannerMetaX.addPattern(new Pattern(base, PatternType.STRIPE_BOTTOM));
        bannerMetaX.addPattern(new Pattern(cima, PatternType.STRIPE_CENTER));
        bannerMetaX.addPattern(new Pattern(base, PatternType.CROSS));
        bannerMetaX.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMetaX.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        bannerX.setItemMeta(bannerMetaX);

        ItemStack bannerY = new ItemStack(Material.BANNER);
        BannerMeta bannerMetaY = (BannerMeta) bannerY.getItemMeta();
        bannerMetaY.setBaseColor(cima);
        bannerMetaY.addPattern(new Pattern(base, PatternType.CROSS));
        bannerMetaY.addPattern(new Pattern(cima, PatternType.HALF_VERTICAL_MIRROR));
        bannerMetaY.addPattern(new Pattern(base, PatternType.STRIPE_DOWNLEFT));
        bannerMetaY.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMetaY.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        bannerY.setItemMeta(bannerMetaY);

        ItemStack bannerZ = new ItemStack(Material.BANNER);
        BannerMeta bannerMetaZ = (BannerMeta) bannerZ.getItemMeta();
        bannerMetaZ.setBaseColor(cima);
        bannerMetaZ.addPattern(new Pattern(base, PatternType.STRIPE_TOP));
        bannerMetaZ.addPattern(new Pattern(base, PatternType.STRIPE_BOTTOM));
        bannerMetaZ.addPattern(new Pattern(base, PatternType.STRIPE_DOWNLEFT));
        bannerMetaZ.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMetaZ.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        bannerZ.setItemMeta(bannerMetaZ);

        ItemStack banner1 = new ItemStack(Material.BANNER);
        BannerMeta bannerMeta1 = (BannerMeta) banner1.getItemMeta();
        bannerMeta1.setBaseColor(cima);
        bannerMeta1.addPattern(new Pattern(base, PatternType.SQUARE_TOP_LEFT));
        bannerMeta1.addPattern(new Pattern(base, PatternType.STRIPE_CENTER));
        bannerMeta1.addPattern(new Pattern(base, PatternType.STRIPE_BOTTOM));
        bannerMeta1.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMeta1.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        banner1.setItemMeta(bannerMeta1);

        ItemStack banner2 = new ItemStack(Material.BANNER);
        BannerMeta bannerMeta2 = (BannerMeta) banner2.getItemMeta();
        bannerMeta2.setBaseColor(cima);
        bannerMeta2.addPattern(new Pattern(base, PatternType.STRIPE_TOP));
        bannerMeta2.addPattern(new Pattern(cima, PatternType.RHOMBUS_MIDDLE));
        bannerMeta2.addPattern(new Pattern(base, PatternType.STRIPE_DOWNLEFT));
        bannerMeta2.addPattern(new Pattern(base, PatternType.STRIPE_BOTTOM));
        bannerMeta2.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMeta2.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        banner2.setItemMeta(bannerMeta2);

        ItemStack banner3 = new ItemStack(Material.BANNER);
        BannerMeta bannerMeta3 = (BannerMeta) banner3.getItemMeta();
        bannerMeta3.setBaseColor(cima);
        bannerMeta3.addPattern(new Pattern(base, PatternType.STRIPE_MIDDLE));
        bannerMeta3.addPattern(new Pattern(cima, PatternType.STRIPE_LEFT));
        bannerMeta3.addPattern(new Pattern(base, PatternType.STRIPE_BOTTOM));
        bannerMeta3.addPattern(new Pattern(base, PatternType.STRIPE_RIGHT));
        bannerMeta3.addPattern(new Pattern(base, PatternType.STRIPE_TOP));
        bannerMeta3.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMeta3.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        banner3.setItemMeta(bannerMeta3);

        ItemStack banner4 = new ItemStack(Material.BANNER);
        BannerMeta bannerMeta4 = (BannerMeta) banner4.getItemMeta();
        bannerMeta4.setBaseColor(base);
        bannerMeta4.addPattern(new Pattern(cima, PatternType.HALF_HORIZONTAL));
        bannerMeta4.addPattern(new Pattern(base, PatternType.STRIPE_LEFT));
        bannerMeta4.addPattern(new Pattern(cima, PatternType.STRIPE_BOTTOM));
        bannerMeta4.addPattern(new Pattern(base, PatternType.STRIPE_RIGHT));
        bannerMeta4.addPattern(new Pattern(base, PatternType.STRIPE_MIDDLE));
        bannerMeta4.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMeta4.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        banner4.setItemMeta(bannerMeta4);

        ItemStack banner5 = new ItemStack(Material.BANNER);
        BannerMeta bannerMeta5 = (BannerMeta) banner5.getItemMeta();
        bannerMeta5.setBaseColor(cima);
        bannerMeta5.addPattern(new Pattern(base, PatternType.STRIPE_BOTTOM));
        bannerMeta5.addPattern(new Pattern(base, PatternType.STRIPE_DOWNRIGHT));
        bannerMeta5.addPattern(new Pattern(cima, PatternType.CURLY_BORDER));
        bannerMeta5.addPattern(new Pattern(base, PatternType.SQUARE_BOTTOM_LEFT));
        bannerMeta5.addPattern(new Pattern(base, PatternType.STRIPE_TOP));
        bannerMeta5.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMeta5.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        banner5.setItemMeta(bannerMeta5);

        ItemStack banner6 = new ItemStack(Material.BANNER);
        BannerMeta bannerMeta6 = (BannerMeta) banner6.getItemMeta();
        bannerMeta6.setBaseColor(cima);
        bannerMeta6.addPattern(new Pattern(base, PatternType.STRIPE_RIGHT));
        bannerMeta6.addPattern(new Pattern(cima, PatternType.HALF_HORIZONTAL));
        bannerMeta6.addPattern(new Pattern(base, PatternType.STRIPE_BOTTOM));
        bannerMeta6.addPattern(new Pattern(base, PatternType.STRIPE_MIDDLE));
        bannerMeta6.addPattern(new Pattern(base, PatternType.STRIPE_LEFT));
        bannerMeta6.addPattern(new Pattern(base, PatternType.STRIPE_TOP));
        bannerMeta6.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMeta6.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        banner6.setItemMeta(bannerMeta6);

        ItemStack banner7 = new ItemStack(Material.BANNER);
        BannerMeta bannerMeta7 = (BannerMeta) banner7.getItemMeta();
        bannerMeta7.setBaseColor(cima);
        bannerMeta7.addPattern(new Pattern(base, PatternType.STRIPE_TOP));
        bannerMeta7.addPattern(new Pattern(cima, PatternType.DIAGONAL_RIGHT));
        bannerMeta7.addPattern(new Pattern(base, PatternType.STRIPE_DOWNLEFT));
        bannerMeta7.addPattern(new Pattern(base, PatternType.SQUARE_BOTTOM_LEFT));
        bannerMeta7.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMeta7.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        banner7.setItemMeta(bannerMeta7);

        ItemStack banner8 = new ItemStack(Material.BANNER);
        BannerMeta bannerMeta8 = (BannerMeta) banner8.getItemMeta();
        bannerMeta8.setBaseColor(cima);
        bannerMeta8.addPattern(new Pattern(base, PatternType.STRIPE_LEFT));
        bannerMeta8.addPattern(new Pattern(base, PatternType.STRIPE_BOTTOM));
        bannerMeta8.addPattern(new Pattern(base, PatternType.STRIPE_RIGHT));
        bannerMeta8.addPattern(new Pattern(base, PatternType.STRIPE_TOP));
        bannerMeta8.addPattern(new Pattern(base, PatternType.STRIPE_MIDDLE));
        bannerMeta8.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMeta8.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        banner8.setItemMeta(bannerMeta8);

        ItemStack banner9 = new ItemStack(Material.BANNER);
        BannerMeta bannerMeta9 = (BannerMeta) banner9.getItemMeta();
        bannerMeta9.setBaseColor(cima);
        bannerMeta9.addPattern(new Pattern(base, PatternType.STRIPE_LEFT));
        bannerMeta9.addPattern(new Pattern(cima, PatternType.HALF_HORIZONTAL_MIRROR));
        bannerMeta9.addPattern(new Pattern(base, PatternType.STRIPE_MIDDLE));
        bannerMeta9.addPattern(new Pattern(base, PatternType.STRIPE_TOP));
        bannerMeta9.addPattern(new Pattern(base, PatternType.STRIPE_RIGHT));
        bannerMeta9.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMeta9.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        banner9.setItemMeta(bannerMeta9);

        ItemStack banner0 = new ItemStack(Material.BANNER);
        BannerMeta bannerMeta0 = (BannerMeta) banner0.getItemMeta();
        bannerMeta0.setBaseColor(cima);
        bannerMeta0.addPattern(new Pattern(base, PatternType.STRIPE_TOP));
        bannerMeta0.addPattern(new Pattern(base, PatternType.STRIPE_RIGHT));
        bannerMeta0.addPattern(new Pattern(base, PatternType.STRIPE_BOTTOM));
        bannerMeta0.addPattern(new Pattern(base, PatternType.STRIPE_LEFT));
        bannerMeta0.addPattern(new Pattern(base, PatternType.STRIPE_DOWNLEFT));
        bannerMeta0.addPattern(new Pattern(cima, PatternType.BORDER));
        bannerMeta0.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        banner0.setItemMeta(bannerMeta0);

        HashMap<Character, ItemStack> map;
        map = redBanners;

        map.put('A', bannerA);
        map.put('B', bannerB);
        map.put('C', bannerC);
        map.put('D', bannerD);
        map.put('E', bannerE);
        map.put('F', bannerF);
        map.put('G', bannerG);
        map.put('H', bannerH);
        map.put('I', bannerI);
        map.put('J', bannerJ);
        map.put('K', bannerK);
        map.put('L', bannerL);
        map.put('M', bannerM);
        map.put('N', bannerN);
        map.put('O', bannerO);
        map.put('P', bannerP);
        map.put('Q', bannerQ);
        map.put('R', bannerR);
        map.put('S', bannerS);
        map.put('T', bannerT);
        map.put('U', bannerU);
        map.put('V', bannerV);
        map.put('W', bannerW);
        map.put('X', bannerX);
        map.put('Y', bannerY);
        map.put('Z', bannerZ);
        map.put('0', banner0);
        map.put('1', banner1);
        map.put('2', banner2);
        map.put('3', banner3);
        map.put('4', banner4);
        map.put('5', banner5);
        map.put('6', banner6);
        map.put('7', banner7);
        map.put('8', banner8);
        map.put('9', banner9);
    }
    public static ItemStack getRedBanner(String string) {
        Character c = ChatColor.stripColor(string.toUpperCase()).substring(0, 1).charAt(0);
        return redBanners.get(c);
    }
}
