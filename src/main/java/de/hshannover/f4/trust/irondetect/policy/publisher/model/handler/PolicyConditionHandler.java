package de.hshannover.f4.trust.irondetect.policy.publisher.model.handler;

import static de.hshannover.f4.trust.irondetect.policy.publisher.util.PolicyStrings.DEFAULT_ADMINISTRATIVE_DOMAIN;

import java.util.List;

import de.hshannover.f4.trust.ifmapj.exception.UnmarshalException;
import de.hshannover.f4.trust.irondetect.policy.publisher.model.identifier.Condition;
import de.hshannover.f4.trust.irondetect.policy.publisher.model.identifier.ExtendedIdentifier;

/**
 * An {@link PolicyConditionHandler} is an {@link PolicyHandler}. It transforms an irondetect policy
 * {@link de.hshannover.f4.trust.irondetect.model.Condition} to an {@link ExtendedIdentifier}-{@link Condition}.
 *
 * @author Marcel Reichenbach
 */
public class PolicyConditionHandler implements PolicyHandler<de.hshannover.f4.trust.irondetect.model.Condition> {

	@Override
	public Condition toIdentifier(de.hshannover.f4.trust.irondetect.model.Condition data) {
		String conditionId = data.getId();
		List<String> expressions = HandlerHelper.transformConditionExpression(data.getConditionSet());

		Condition identifier = new Condition(conditionId, expressions, DEFAULT_ADMINISTRATIVE_DOMAIN);

		return identifier;
	}

	@Override
	public de.hshannover.f4.trust.irondetect.model.Condition fromIdentifier(ExtendedIdentifier eIdentifier)
			throws UnmarshalException {

		if (eIdentifier instanceof Condition) {
			de.hshannover.f4.trust.irondetect.model.Condition policyData =
					new de.hshannover.f4.trust.irondetect.model.Condition();
			policyData.setId(((Condition) eIdentifier).getID());
			policyData.setConditionSet(
					HandlerHelper.retransformConditionExpression(((Condition) eIdentifier).getExpressions()));

			return policyData;

		} else {
			throw new UnmarshalException("False argument this handler is only for Condition ExtendedIdentifier");
		}
	}

	@Override
	public Class<de.hshannover.f4.trust.irondetect.model.Condition> handle() {
		return de.hshannover.f4.trust.irondetect.model.Condition.class;
	}

}
