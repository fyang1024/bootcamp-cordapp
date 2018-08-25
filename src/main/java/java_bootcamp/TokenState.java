package java_bootcamp;

import com.google.common.collect.ImmutableList;
import net.corda.core.contracts.ContractState;
import net.corda.core.identity.AbstractParty;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/* Our state, defining a shared fact on the ledger.
 * See src/main/kotlin/examples/IAmAState.java and
 * src/main/kotlin/examples/IAmAlsoAState.java for examples. */
public class TokenState implements ContractState {

    private AbstractParty issuer, recipient;
    private int amount;

    public TokenState(AbstractParty issuer, AbstractParty recipient, int amount) {
        this.issuer = issuer;
        this.recipient = recipient;
        this.amount = amount;
    }

    @NotNull
    @Override
    public List<AbstractParty> getParticipants() {
        return ImmutableList.of(issuer, recipient);
    }

    public AbstractParty getOwner() {
        return getRecipient();
    }

    public AbstractParty getIssuer() {
        return issuer;
    }

    public AbstractParty getRecipient() {
        return recipient;
    }

    public int getAmount() {
        return amount;
    }
}