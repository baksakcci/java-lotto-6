package lotto.controller;

import camp.nextstep.edu.missionutils.Console;
import lotto.controller.dto.PurchaseHistoryDto;
import lotto.model.LottoStore;
import lotto.model.vo.BonusNumber;
import lotto.model.vo.Money;
import lotto.model.Player;
import lotto.model.RandomNumberGenerateStrategy;
import lotto.model.vo.WinNumber;
import lotto.view.ErrorView;
import lotto.view.OutputView;

public class LottoGameController {

    private OutputView outputView;
    private ErrorView errorView;

    public LottoGameController(OutputView outputView, ErrorView errorView) {
        this.outputView = outputView;
        this.errorView = errorView;
    }

    public void run() {
        Player player = buyLotto();
        WinNumber winNumber = setWinNumber();
        BonusNumber bonusNumber = setBonusNumber();
    }

    private Player buyLotto() {
        Player player = null;
        try {
            // 구입 금액 입력
            outputView.printPurchaseInput();
            Money money = new Money(input());
            player = Player.of(money);
            // 로또 구매
            player.buyLotto(LottoStore.of(new RandomNumberGenerateStrategy()));
            // 로또 번호 반환 및 출력
            PurchaseHistoryDto dto = PurchaseHistoryDto.toDto(player.getEA(), player.getHistory());
            outputView.printPurchaseHistory(dto);
        } catch (IllegalArgumentException e) {
            errorView.printErrorMessage(e.getMessage());
            buyLotto();
        }
        outputView.printLineSeparator();
        return player;
    }

    private WinNumber setWinNumber() {
        WinNumber winNumber = null;
        try {
            outputView.printWinNumberInput();
            winNumber = WinNumber.of(input());
        } catch (IllegalArgumentException e) {
            errorView.printErrorMessage(e.getMessage());
            setWinNumber();
        }
        outputView.printLineSeparator();
        return winNumber;
    }

    private BonusNumber setBonusNumber() {
        BonusNumber bonusNumber = null;
        try {
            outputView.printBonusNumberInput();
            bonusNumber = BonusNumber.of(input());
        } catch (IllegalArgumentException e) {
            errorView.printErrorMessage(e.getMessage());
            setBonusNumber();
        }
        outputView.printLineSeparator();
        return bonusNumber;
    }

    private String input() {
        String input = Console.readLine();
        if (input.isBlank()) {
            throw new IllegalArgumentException("빈 값을 입력하면 안됩니다.");
        }
        return input;
    }
}
