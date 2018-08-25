package java_bootcamp;

import net.corda.core.contracts.Command;
import net.corda.core.contracts.CommandData;
import net.corda.core.contracts.Contract;
import net.corda.core.transactions.LedgerTransaction;

import java.security.PublicKey;
import java.util.List;

import static net.corda.core.contracts.ContractsDSL.requireThat;

/* Our contract, governing how our state will evolve over time.
 * See src/main/kotlin/examples/ExampleContract.java for an example. */
public class TokenContract implements Contract {
    public static String ID = "java_bootcamp.TokenContract";

    public static class Issue implements CommandData {}

    @Override
    public void verify(LedgerTransaction tx) throws IllegalArgumentException {
        requireThat( requirements -> {
            // shape requirements
            requirements.using("transaction has only one output", tx.getOutputs().size() == 1);
            requirements.using("transaction has no input", tx.getInputs().isEmpty());
            requirements.using("transaction command is Issue", tx.commandsOfType(Issue.class).size() == 1);

            //content requirements
            requirements.using("transaction output is TokenState", tx.outputsOfType(TokenState.class).size() == 1);

            TokenState output = (TokenState) tx.getOutput(0);
            requirements.using("transaction output amount is positive", output.getAmount() > 0);

            //signer requirements

            Command<Issue> issueCommand = tx.commandsOfType(Issue.class).get(0);
            List<PublicKey> signers = issueCommand.getSigners();
            requirements.using("transaction signer is issuer", signers.contains(output.getIssuer().getOwningKey()));
//
            return null;
        } );

    }
}