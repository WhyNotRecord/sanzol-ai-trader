package binance.futures.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountInfo {
  /**
   * account commission tier
   */
  private int feeTier;
  private boolean canTrade;
  private boolean canDeposit;
  private boolean canWithdraw;
  private boolean multiAssetsMargin;
  /**
   * total initial margin required with current mark price (useless with isolated positions), only for USDT asset
   */
  private BigDecimal totalInitialMargin;
  /**
   * total maintenance margin required, only for USDT asset
   */
  private BigDecimal totalMaintMargin;
  /**
   * total wallet balance, only for USDT asset
   */
  private BigDecimal totalWalletBalance;
  /**
   * total unrealized profit, only for USDT asset
   */
  private BigDecimal totalUnrealizedProfit;
  /**
   * total margin balance, only for USDT asset
   */
  private BigDecimal totalMarginBalance;
  /**
   * initial margin required for positions with current mark price, only for USDT asset
   */
  private BigDecimal totalPositionInitialMargin;
  /**
   * initial margin required for open orders with current mark price, only for USDT asset
   */
  private BigDecimal totalOpenOrderInitialMargin;
  /**
   * crossed wallet balance, only for USDT asset
   */
  private BigDecimal totalCrossWalletBalance;
  /**
   * unrealized profit of crossed positions, only for USDT asset
   */
  private BigDecimal totalCrossUnPnl;
  /**
   * available balance, only for USDT asset
   */
  private BigDecimal availableBalance;
  /**
   * maximum amount for transfer out, only for USDT asset
   */
  private BigDecimal maxWithdrawAmount;
  /**
   * positions of all symbols in the market are returned
   *  only "BOTH" positions will be returned with One-way mode
   *  only "LONG" and "SHORT" positions will be returned with Hedge mode
   */
  private List<AccountPosition> positions;

  public boolean canTrade() {
    return canTrade;
  }

  public List<AccountPosition> getPositions() {
    if (positions == null)
      return null;
    return positions.stream().filter(pos -> pos.positionAmt != 0).collect(Collectors.toList());
  }

  public List<AccountPosition> getAllPositions() {
    if (positions == null)
      return null;
    return new ArrayList<>(positions);
  }

  public static class AccountPosition {
    public String symbol;// symbol name
    public float initialMargin;// initial margin required with current mark price
    public float maintMargin;// maintenance margin required
    public float unrealizedProfit;// unrealized profit
    public float positionInitialMargin;// initial margin required for positions with current mark price
    public float openOrderInitialMargin;// initial margin required for open orders with current mark price
    public int leverage;// current initial leverage
    public boolean isolated;// if the position is isolated
    public float entryPrice;// average entry price
    public float maxNotional;// maximum available notional with current leverage
    public String positionSide;// position side
    public float positionAmt;// position amount
    public long updateTime;// last update time

    @Override
    public String toString() {
      return positionSide + "\t\t" + positionAmt + "\t\t" + symbol + "\t\t" +
          getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }
  }

  public BigDecimal getAvailableBalance() {
    return availableBalance;
  }

  public void setAvailableBalance(BigDecimal availableBalance) {
    this.availableBalance = availableBalance;
  }

  public BigDecimal getMaxWithdrawAmount() {
    return maxWithdrawAmount;
  }

  public void setMaxWithdrawAmount(BigDecimal maxWithdrawAmount) {
    this.maxWithdrawAmount = maxWithdrawAmount;
  }

  public int getFeeTier() {
    return feeTier;
  }

  public void setFeeTier(int feeTier) {
    this.feeTier = feeTier;
  }

  public void setCanTrade(boolean canTrade) {
    this.canTrade = canTrade;
  }

  public boolean canDeposit() {
    return canDeposit;
  }

  public void setCanDeposit(boolean canDeposit) {
    this.canDeposit = canDeposit;
  }

  public boolean canWithdraw() {
    return canWithdraw;
  }

  public void setCanWithdraw(boolean canWithdraw) {
    this.canWithdraw = canWithdraw;
  }

  public boolean isMultiAssetsMargin() {
    return multiAssetsMargin;
  }

  public void setMultiAssetsMargin(boolean multiAssetsMargin) {
    this.multiAssetsMargin = multiAssetsMargin;
  }

  public BigDecimal getTotalInitialMargin() {
    return totalInitialMargin;
  }

  public void setTotalInitialMargin(BigDecimal totalInitialMargin) {
    this.totalInitialMargin = totalInitialMargin;
  }

  public BigDecimal getTotalMaintMargin() {
    return totalMaintMargin;
  }

  public void setTotalMaintMargin(BigDecimal totalMaintMargin) {
    this.totalMaintMargin = totalMaintMargin;
  }

  public BigDecimal getTotalWalletBalance() {
    return totalWalletBalance;
  }

  public void setTotalWalletBalance(BigDecimal totalWalletBalance) {
    this.totalWalletBalance = totalWalletBalance;
  }

  public BigDecimal getTotalUnrealizedProfit() {
    return totalUnrealizedProfit;
  }

  public void setTotalUnrealizedProfit(BigDecimal totalUnrealizedProfit) {
    this.totalUnrealizedProfit = totalUnrealizedProfit;
  }

  public BigDecimal getTotalMarginBalance() {
    return totalMarginBalance;
  }

  public void setTotalMarginBalance(BigDecimal totalMarginBalance) {
    this.totalMarginBalance = totalMarginBalance;
  }

  public BigDecimal getTotalPositionInitialMargin() {
    return totalPositionInitialMargin;
  }

  public void setTotalPositionInitialMargin(BigDecimal totalPositionInitialMargin) {
    this.totalPositionInitialMargin = totalPositionInitialMargin;
  }

  public BigDecimal getTotalOpenOrderInitialMargin() {
    return totalOpenOrderInitialMargin;
  }

  public void setTotalOpenOrderInitialMargin(BigDecimal totalOpenOrderInitialMargin) {
    this.totalOpenOrderInitialMargin = totalOpenOrderInitialMargin;
  }

  public BigDecimal getTotalCrossWalletBalance() {
    return totalCrossWalletBalance;
  }

  public void setTotalCrossWalletBalance(BigDecimal totalCrossWalletBalance) {
    this.totalCrossWalletBalance = totalCrossWalletBalance;
  }

  public BigDecimal getTotalCrossUnPnl() {
    return totalCrossUnPnl;
  }

  public void setTotalCrossUnPnl(BigDecimal totalCrossUnPnl) {
    this.totalCrossUnPnl = totalCrossUnPnl;
  }
}
