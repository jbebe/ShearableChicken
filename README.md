### How to replace chicken with new chicken?

1. Replace `EntityType.CHICKEN` with `ShearableChickenEntity` cast to `ChickenEntity`
   * Do it on Server and Client too
   * While EntityType is in Registry and not yet registered, you cannot change them. You have to change it after they are created.
   * [x] **TODO: Find out which event is fired after registry init and replace chicken there**  
2. Replace RenderingRegistry.INSTANCE.entityRenderers[EntityType<ChickenEntity>] with ShearableChickenRenderer
   * [x] **TODO: Do it on client only (ClientSetup.java)**  
3. **[ ] TODO: Replace fields in EntityType<ChickenEntity> instead of the whole object**
